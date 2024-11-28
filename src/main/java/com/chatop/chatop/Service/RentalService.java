package com.chatop.chatop.Service;

import com.chatop.chatop.Model.Rental;
import com.chatop.chatop.Repository.RentalRepository;
import com.chatop.chatop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Optional;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;

    public String createRental(String name, Float surface, Float price, String picture, String description){
        Rental n = new Rental();
        n.setName(name);
        n.setSurface(surface);
        n.setPrice(price);
        n.setPicture(picture);
        n.setDescription(description);
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());

        //n.setOwner();
        rentalRepository.save(n);
        return "OK";
    }

    public Iterable<Rental> displayRentals(){
        return rentalRepository.findAll();
    }

    public Optional<Rental> displayRentalById(@PathVariable Integer id){
        return rentalRepository.findById(Long.valueOf(id));
    }
}
