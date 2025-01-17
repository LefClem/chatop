package com.chatop.chatop.Service;

import com.chatop.chatop.DTO.MessageDtoRequest;
import com.chatop.chatop.DTO.MessageDtoResponse;
import com.chatop.chatop.Entity.Message;
import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Model.MessageRequest;
import com.chatop.chatop.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public MessageDtoResponse createMessage(MessageDtoRequest messageDtoRequest, Integer id){
        Message n = new Message();
        n.setMessage(messageDtoRequest.getMessage());
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());
        n.setUser_id(id);
        n.setRental_id(messageDtoRequest.getRental_id());
        messageRepository.save(n);
        MessageDtoResponse messageDtoResponse = new MessageDtoResponse();
        messageDtoResponse.setMessage("Votre message à bien été envoyé : " + n.getMessage());
        return messageDtoResponse;
    }
}
