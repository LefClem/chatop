package com.chatop.chatop.Controller;

import com.chatop.chatop.DTO.MessageDtoRequest;
import com.chatop.chatop.DTO.MessageDtoResponse;
import com.chatop.chatop.DTO.UserDTO;
import com.chatop.chatop.Entity.Message;
import com.chatop.chatop.Service.MessageService;
import com.chatop.chatop.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path="/api/messages")
@SecurityRequirement(name="bearerAuth")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    private UserDTO getAuthenticatedUser(){
        return userService.getAuthUser().get();
    }

    @Operation(summary = "Message creation", description = "Create a new message in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "message : messsage send with success",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping(path="")
    public @ResponseBody ResponseEntity<MessageDtoResponse> addNewMessage(@RequestBody MessageDtoRequest messageDtoRequest){
        try{
            Integer id = getAuthenticatedUser().getId();
            return ResponseEntity.ok(messageService.createMessage(messageDtoRequest, id));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
