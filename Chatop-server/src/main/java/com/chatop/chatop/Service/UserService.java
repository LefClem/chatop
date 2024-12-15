package com.chatop.chatop.Service;

import com.chatop.chatop.DTO.UserDTO;
import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Model.RegisterRequest;
import com.chatop.chatop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(RegisterRequest registerRequest) {
        User n = new User();
        n.setName(registerRequest.name);
        n.setEmail(registerRequest.email);
        n.setPassword(passwordEncoder.encode(registerRequest.password));
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());
        return userRepository.save(n);
    }

    public Optional<UserDTO> getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Optional<User> user = userRepository.findByEmail(jwt.getSubject());

        return  user.map(this::convertToDTO);
    }

    public Optional<UserDTO> getUserInfosById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO);
    }

    public UserDTO convertToDTO (User user){
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());

        return dto;
    }
}
