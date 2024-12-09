package com.chatop.chatop.Service;

import com.chatop.chatop.DTO.RentalsListDto;
import com.chatop.chatop.Entity.Rental;
import com.chatop.chatop.Model.RentalDTO;
import com.chatop.chatop.Repository.RentalRepository;
import com.chatop.chatop.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${app.base_url.dev}")
    private String baseUrl;

    public String formatUrlPicture(String picture){
//        TODO: check for the picture url !
        return baseUrl + picture;
    }

    public Rental createRental(String name, Float surface, Float price, MultipartFile picture, String description, Integer id) throws IOException {
        Rental n = new Rental();
        String filename = UUID.randomUUID().toString() + "_" + picture.getOriginalFilename();
        Path filePath = Paths.get("uploads/" + filename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, picture.getBytes());

        n.setName(name);
        n.setSurface(surface);
        n.setPrice(price);
        n.setPicture("/uploads/" + filename);
        n.setDescription(description);
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());

        n.setOwner_id(id);
        return rentalRepository.save(n);
    }

    public String updateRental(String name, Float surface, Float price, String description, Rental rental){
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setCreated_at(new Date());
        rental.setUpdated_at(new Date());

        rentalRepository.save(rental);
        return "OK";
    }

    public RentalsListDto displayRentals(){
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalsDtos = new ArrayList<>();
        for (Rental rental : rentals) {
            RentalDTO rentalsDto = modelMapper.map(rental, RentalDTO.class);
            rentalsDtos.add(rentalsDto);
        }
        RentalsListDto rentalsListDto = new RentalsListDto();
        rentalsListDto.setRentals(rentalsDtos);
        return rentalsListDto;
    }

    public Optional<RentalDTO> displayRentalById(@PathVariable Integer id){
        Optional<Rental> rental = rentalRepository.findById(Long.valueOf(id));
        return rental.map(this::convertToDTO);
    }

    public RentalDTO convertToDTO (Rental rental){
        RentalDTO dto = new RentalDTO();

        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwner_id(rental.getOwner_id());
        dto.setCreated_at(rental.getCreated_at());
        dto.setUpdated_at(rental.getUpdated_at());

        return dto;
    }
}