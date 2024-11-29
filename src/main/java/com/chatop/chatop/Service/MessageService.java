package com.chatop.chatop.Service;

import com.chatop.chatop.Entity.Message;
import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(String message, User id){
        Message n = new Message();
        n.setMessage(message);
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());
        n.setUser(id);
        return messageRepository.save(n);
    }
}
