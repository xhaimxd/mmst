package com.roger.mmst.component.message;

import com.roger.mmst.dto.message.WsMessage;
import jakarta.annotation.Resource;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class WsMessagePublisher {

    private final ConcurrentMap<String, String> userToSessionId = new ConcurrentHashMap<>();

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    public void publish(String sessionId, WsMessage payload) {
        userToSessionId.entrySet().stream()
                .filter(e -> e.getValue().equals(sessionId))
                .findFirst()
                .ifPresent(entry -> simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/messages", payload, createHeaders(entry.getValue(), entry.getKey())));
    }

    public void register(String sessionId, Principal principal) {
        String user = principal.getName();
        String existSessionId = userToSessionId.get(user);
        if (existSessionId != null) {
            simpMessagingTemplate.convertAndSendToUser(user, "/queue/messages", "您已在其他地方登录，请重新登录！", createHeaders(existSessionId, user));
            simpMessagingTemplate.convertAndSend("/app/disconnect", "", createDisconnectHeaders(existSessionId));
        }
        userToSessionId.put(user, sessionId);
    }

    public void unregister(String user) {
        userToSessionId.remove(user);
    }

    private MessageHeaders createHeaders(String sessionId, String user) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setUser(() -> user);
        return headerAccessor.getMessageHeaders();
    }

    private MessageHeaders createDisconnectHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.DISCONNECT);
        headerAccessor.setSessionId(sessionId);
        return headerAccessor.getMessageHeaders();
    }
}
