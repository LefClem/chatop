package com.chatop.chatop.Service;

import com.chatop.chatop.Entity.Rental;
import com.chatop.chatop.Entity.User;
import com.chatop.chatop.Model.RentalDTO;
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

    public String createRental(RentalDTO rentalDTO, User id){
        Rental n = new Rental();
        n.setName(rentalDTO.name);
        n.setSurface(rentalDTO.surface);
        n.setPrice(rentalDTO.price);
        n.setPicture(rentalDTO.picture);
        n.setDescription(rentalDTO.description);
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());

        n.setOwner(id);
        rentalRepository.save(n);
        return "OK";
    }

    public String updateRental(RentalDTO rentalDTO, Rental rental){
        rental.setName(rentalDTO.name);
        rental.setSurface(rentalDTO.surface);
        rental.setPrice(rentalDTO.price);
        rental.setPicture(rentalDTO.picture);
        rental.setDescription(rentalDTO.description);
        rental.setCreated_at(new Date());
        rental.setUpdated_at(new Date());

        //n.setOwner();
        rentalRepository.save(rental);
        return "OK";
    }

    public Iterable<Rental> displayRentals(){
        return rentalRepository.findAll();
    }

    public Optional<Rental> displayRentalById(@PathVariable Integer id){
        return rentalRepository.findById(Long.valueOf(id));
    }
}
