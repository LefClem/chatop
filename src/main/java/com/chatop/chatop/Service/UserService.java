package com.chatop.chatop.Service;

import com.chatop.chatop.Model.User;
import com.chatop.chatop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String name, String email, String password) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setPassword(password);
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());
        return userRepository.save(n);
    }
}
