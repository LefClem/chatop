package com.chatop.chatop.Controller;

import com.chatop.chatop.DTO.UserDTO;
import com.chatop.chatop.Repository.UserRepository;
import com.chatop.chatop.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Operation(summary = "User id", description = "Return the informations of a user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a user",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<UserDTO>> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserInfosById(id));
    }
}