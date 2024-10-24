package com.roger.mmst.component.conf.security;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 @author Roger Liu
 @date 2024/03/06
 */
@Component
@Slf4j
public class JWTWebsocketAuthManager implements AuthenticationManager {

    private static final String BEARER = "Bearer ";
    @Resource
    private JWT jwt;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JWSAuthenticationToken token = (JWSAuthenticationToken) authentication;
        String tokenString = (String) token.getCredentials();
        log.info("auth token: {}", tokenString);
        final Long id = jwt.verify(tokenString.substring(BEARER.length()));
        final AuthUser user = new AuthUser();
        user.setId(id);
        token = new JWSAuthenticationToken(tokenString, user, List.of());
        token.setAuthenticated(true);
        return token;
    }
}
