package com.chatop.chatop.Controller;

import com.chatop.chatop.Model.Rental;
import com.chatop.chatop.Service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path="/api/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping(path="/")
    public @ResponseBody String addNewRental(@RequestParam String name, @RequestParam Float surface, @RequestParam Float price, @RequestParam String picture, @RequestParam String description){
        try{
            rentalService.createRental(name, surface, price, picture, description);
            return "New rental saved !";
        } catch (Exception e) {
            return "Error : " + e.getMessage();
        }
    }

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Rental> getAllRentals(){
        try{
            return rentalService.displayRentals();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }
}
