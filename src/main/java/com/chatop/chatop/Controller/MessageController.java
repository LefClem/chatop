package com.chatop.chatop.Controller;

import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserController userController;

    private User getAuthenticatedUser(){
        return userController.getUser();
    }


    @PostMapping(path="/")
    public @ResponseBody String addNewMessage(@RequestParam String message){
        try{
            User id = getAuthenticatedUser();
            messageService.createMessage(message, id);

            return "message : " + message;
        }catch (Exception e){
            return "Error :" + e.getMessage();
        }
    }
}
