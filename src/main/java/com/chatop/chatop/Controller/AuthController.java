package com.chatop.chatop.Controller;

import com.chatop.chatop.DTO.TokenDto;
import com.chatop.chatop.DTO.UserDTO;
import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Model.LoginRequest;
import com.chatop.chatop.Model.RegisterRequest;
import com.chatop.chatop.Repository.UserRepository;
import com.chatop.chatop.Service.JWTService;
import com.chatop.chatop.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

        import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/auth")
public class AuthController {
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

    @Operation(summary = "User id", description = "Return the informations of a user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<User> getUserById(@PathVariable Integer id){
        return userRepository.findById(Long.valueOf(id));
    }

    @Operation(summary = "User me", description = "return the credentials of the logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping(path = "/me")
    public @ResponseBody ResponseEntity<Optional<UserDTO>> getUser(){
        return ResponseEntity.ok(userService.getAuthUser());
    }

    @Operation(summary = "User register", description = "Create a user in Database and log him")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping(path="/register") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<TokenDto> addNewUser (@RequestBody RegisterRequest registerRequest) {
        try {
            userService.createUser(registerRequest);
            Optional<User> user = userRepository.findByEmail(registerRequest.email);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(registerRequest.email, registerRequest.password)
            );
            String token = jwtService.generateToken(authentication);
            TokenDto tokenDto = new TokenDto(token);
            return ResponseEntity.ok(tokenDto);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "User login", description = "Authenticate user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDto> getToken(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
            );
            String token = jwtService.generateToken(authentication);
            TokenDto tokenDto = new TokenDto(token);
            return ResponseEntity.ok(tokenDto);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid Username or password");
        }
    }
}
