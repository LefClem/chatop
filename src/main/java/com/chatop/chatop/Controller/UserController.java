package com.chatop.chatop.Controller;

import com.chatop.chatop.Model.User;
import com.chatop.chatop.Repository.UserRepository;
import com.chatop.chatop.Service.JWTService;
import com.chatop.chatop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public @ResponseBody Optional<User> getUserName (@PathVariable Integer id){
        return userRepository.findById(Long.valueOf(id));
    }

    @PostMapping(path="/register") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email, @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        try {
            userService.createUser(name, email, passwordEncoder.encode(password));
            return "Saved";
        } catch(Exception e){
            return "Error :" + e.getMessage();
        }
    }

    @PostMapping("/login")
    public String getToken(@RequestParam String email, @RequestParam String password) {
        try {
            System.out.println("Authenticating user with email: " + email);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            System.out.println("Authentication successful for user: " + authentication.getName());
            return jwtService.generateToken(authentication);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid Username or password");
        }
    }
}
