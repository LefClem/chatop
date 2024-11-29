package com.chatop.chatop.Controller;

import com.chatop.chatop.Entity.Rental;
import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Model.RentalDTO;
import com.chatop.chatop.Repository.RentalRepository;
import com.chatop.chatop.Service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private  RentalRepository rentalRepository;
    @Autowired
    private UserController userController;

    private User getAuthenticatedUser(){
        return userController.getUser();
    }

    @PostMapping(path="/")
    public @ResponseBody String addNewRental(@RequestBody RentalDTO rentalDTO){
        try{
            User id = getAuthenticatedUser();
            rentalService.createRental(rentalDTO, id);
            return "Rental created !";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody String updateRental(@PathVariable Integer id, @RequestBody RentalDTO rentalDTO){
        try {
            Optional<Rental> rentalOptional = rentalRepository.findById(Long.valueOf(id));
                if(rentalOptional.isPresent()){
                    Rental rental = rentalOptional.get();
                    if(rental.getOwner().getId().equals(getAuthenticatedUser().getId())){
                        rentalService.updateRental(rentalDTO, rental);
                    } else {
                        return "You are not authorized to do this operation !";
                    }
                }
                return "Rental updated !";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    };

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Rental> getAllRentals(){
        try{
            return rentalService.displayRentals();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<Rental> getRentalById(@PathVariable Integer id){
        try {
            return rentalService.displayRentalById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }
}
