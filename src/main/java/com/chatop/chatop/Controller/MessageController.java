package com.chatop.chatop.Controller;

import com.chatop.chatop.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping(path="/")
    public @ResponseBody String addNewMessage(@RequestParam String message){
        try{
            messageService.createMessage(message);
            return "New message added";
        }catch (Exception e){
            return "Error :" + e.getMessage();
        }
    }
}
