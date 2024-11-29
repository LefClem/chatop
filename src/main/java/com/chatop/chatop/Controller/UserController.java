package com.chatop.chatop.Controller;

import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Repository.UserRepository;
import com.chatop.chatop.Service.JWTService;
import com.chatop.chatop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<User> getUserById(@PathVariable Integer id){
        return userRepository.findById(Long.valueOf(id));
    }

    @GetMapping(path = "/me")
    public @ResponseBody User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Optional<User> user = userRepository.findByEmail(jwt.getSubject());
            return user.get();
    }

    @PostMapping(path="/register") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email, @RequestParam String password) {
        try {
            userService.createUser(name, email, passwordEncoder.encode(password));
            Optional<User> user = userRepository.findByEmail(email);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            return "Token: " + jwtService.generateToken(authentication);
        } catch(Exception e){
            return "Error :" + e.getMessage();
        }
    }

    @PostMapping("/login")
    public String getToken(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            System.out.println("Authentication successful for user: " + authentication.getName());
            return "Token: " + jwtService.generateToken(authentication);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid Username or password");
        }
    }
}
