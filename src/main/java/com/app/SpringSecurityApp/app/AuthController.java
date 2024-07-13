package com.app.SpringSecurityApp.app;

import com.app.SpringSecurityApp.app.dto.request.LoginRequest;
import com.app.SpringSecurityApp.app.dto.response.AuthResponse;
import com.app.SpringSecurityApp.config.service.UserDetailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v7/auth")
public class AuthController {


    private  final UserDetailServiceImpl userDetailService;

    public AuthController(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> hello(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(userDetailService.login(loginRequest));
    }

    @PostMapping("register")
    public String helloSecured() {
        return "test-register";
    }

}
