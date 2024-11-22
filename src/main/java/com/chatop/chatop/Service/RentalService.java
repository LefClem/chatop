package com.chatop.chatop.Service;

import com.chatop.chatop.Model.Rental;
import com.chatop.chatop.Repository.RentalRepository;
import com.chatop.chatop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRespository;
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
        rentalRespository.save(n);
        return "OK";
    }

    public Iterable<Rental> displayRentals(){
        return rentalRespository.findAll();
    }
}
