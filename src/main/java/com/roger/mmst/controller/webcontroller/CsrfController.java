package com.roger.mmst.controller.webcontroller;

import com.roger.mmst.component.conf.security.JWT;
import com.roger.mmst.obj.dto.R;
import jakarta.annotation.Resource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

    @Resource
    private JWT jwt;

    @GetMapping("/login")
    public R<String> login() {
        return R.ok(jwt.sign(1L));
    }


    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
