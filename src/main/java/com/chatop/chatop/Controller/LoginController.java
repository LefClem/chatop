package com.chatop.chatop.Controller;

import com.chatop.chatop.Service.JWTService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/auth")
public class LoginController {
//    public JWTService jwtService;
//
//    public LoginController(JWTService jwtService) {
//        this.jwtService = jwtService;
//    }
//
//    @PostMapping("/login")
//    public String getToken(Authentication authentication) {
//        String token = jwtService.generateToken(authentication);
//        return token;
//    }
}
