package com.roger.mmst.component.conf;

import com.roger.mmst.component.conf.security.TokenValidateChannelInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import static org.springframework.messaging.simp.SimpMessageType.*;

/**
 * @author Roger Liu
 * @date 2024/03/05
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocketSecurity
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WSConfig implements WebSocketMessageBrokerConfigurer {

    @Resource
    private TokenValidateChannelInterceptor tokenValidateChannelInterceptor;

    @Bean
    AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
                .simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT, HEARTBEAT).permitAll()
//                .simpDestMatchers("/**").permitAll()
                .anyMessage().authenticated();
        return messages.build();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/mmst")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/mmst");
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(64 * 1024);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                Map<String, Object> sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
                CsrfToken expectedToken = (sessionAttributes != null)
                        ? (CsrfToken) sessionAttributes.get(CsrfToken.class.getName()) : null;
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null && expectedToken != null) {
                    accessor.setNativeHeader(expectedToken.getHeaderName(), generateTokenString(expectedToken.getToken()));
                }
                return message;
            }
        });
        registration.interceptors(tokenValidateChannelInterceptor);
    }

    private String generateTokenString(String token) {
        byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
        int tokenSize = tokenBytes.length;

        // Generate random bytes
        byte[] randomBytes = new byte[tokenSize];
        new Random().nextBytes(randomBytes);

        // XOR token with random bytes to create xoredCsrf
        byte[] xoredCsrf = new byte[tokenSize];
        for (int i = 0; i < tokenSize; i++) {
            xoredCsrf[i] = (byte) (tokenBytes[i] ^ randomBytes[i]);
        }

        // Concatenate randomBytes and xoredCsrf
        byte[] resultBytes = new byte[randomBytes.length + xoredCsrf.length];
        System.arraycopy(randomBytes, 0, resultBytes, 0, randomBytes.length);
        System.arraycopy(xoredCsrf, 0, resultBytes, randomBytes.length, xoredCsrf.length);

        // Encode the result in Base64 URL format
        return Base64.getUrlEncoder().withoutPadding().encodeToString(resultBytes);
    }
}
