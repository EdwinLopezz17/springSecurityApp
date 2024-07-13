package com.app.SpringSecurityApp.app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v7/auth")
public class AuthController {

    @PostMapping("login")
    public String hello() {
        return "test-login";
    }

    @PostMapping("register")
    public String helloSecured() {
        return "test-register";
    }

}
