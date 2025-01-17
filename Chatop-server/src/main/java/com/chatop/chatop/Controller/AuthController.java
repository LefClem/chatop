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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


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

    @Operation(summary = "User me", description = "return the credentials of the logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the logged user",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @SecurityRequirement(name="bearerAuth")
    @GetMapping(path = "/me")
    public @ResponseBody ResponseEntity<Optional<UserDTO>> getUser(){
        return ResponseEntity.ok(userService.getAuthUser());
    }

    @Operation(summary = "User register", description = "Create a user in Database and log him")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "token : jwt",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping(path="/register") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<TokenDto> addNewUser (@RequestBody RegisterRequest registerRequest) {
        try {
            userService.createUser(registerRequest);
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
            @ApiResponse(responseCode = "200", description = "token : jwt",
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
            throw new RuntimeException("Invalid Username or password");
        }
    }
}
