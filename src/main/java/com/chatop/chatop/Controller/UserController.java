package com.chatop.chatop.Controller;

import com.chatop.chatop.Model.User;
import com.chatop.chatop.Repository.UserRepository;
import com.chatop.chatop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/user/{id}")
    public @ResponseBody Optional<User> getUserName (@PathVariable Integer id){
        return userRepository.findById(Long.valueOf(id));
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email, @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        try {
            userService.createUser(name, email, password);
            return "Saved";
        } catch(Exception e){
            return "Error :" + e.getMessage();
        }
    }

}
