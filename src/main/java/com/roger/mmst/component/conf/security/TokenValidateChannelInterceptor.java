package com.roger.mmst.component.conf.security;

import com.roger.mmst.component.message.WsMessagePublisher;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class TokenValidateChannelInterceptor implements ChannelInterceptor {

    private static final String AUTH = "Authorization";

    @Resource
    private JWTWebsocketAuthManager jwtWebsocketAuthManager;
    @Resource
    @Lazy
    private WsMessagePublisher wsMessagePublisher;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("ws连接开始鉴权");
            try {
                Optional.ofNullable(accessor.getNativeHeader(AUTH)).ifPresent(t -> {
                    JWSAuthenticationToken token = (JWSAuthenticationToken) jwtWebsocketAuthManager.authenticate(new JWSAuthenticationToken(t.get(0)));
                    String sessionId = SimpMessageHeaderAccessor.getSessionId(message.getHeaders());
                    wsMessagePublisher.register(sessionId, token);
                    accessor.setUser(token);
                });
            } catch (Exception e) {
                log.error("ws连接鉴权失败：{}", e.getMessage());
            }
        }
        return message;
    }
}
