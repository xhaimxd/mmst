package com.roger.mmst.webcontroller;

import com.roger.mmst.conf.security.JWT;
import jakarta.annotation.Resource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

    @Resource
    private JWT jwt;

    @GetMapping("/login")
    public String login() {
        return jwt.sign(1L);
    }


    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
