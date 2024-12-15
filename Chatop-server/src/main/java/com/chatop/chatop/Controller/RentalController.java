package com.chatop.chatop.Controller;

import com.chatop.chatop.DTO.RentalsListDto;
import com.chatop.chatop.DTO.RentalsResponseDto;
import com.chatop.chatop.DTO.UserDTO;
import com.chatop.chatop.Entity.Rental;
import com.chatop.chatop.DTO.RentalDTO;
import com.chatop.chatop.Repository.RentalRepository;
import com.chatop.chatop.Service.RentalService;
import com.chatop.chatop.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private  RentalRepository rentalRepository;
    @Autowired
    private UserService userService;

    private UserDTO getAuthenticatedUser(){
        return userService.getAuthUser().get();
    }

    @Operation(summary = "Rental creation", description = "Add a new rental in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental created !",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping(path="")
    public @ResponseBody RentalsResponseDto addNewRental(
            @RequestParam String name,
            @RequestParam Float surface,
            @RequestParam Float price,
            @RequestParam MultipartFile picture,
            @RequestParam String description
            ){
        try{
            Integer id = getAuthenticatedUser().getId();
            rentalService.createRental(name, surface, price, picture, description, id);
            return RentalsResponseDto.builder().message("Rental created !").build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Rental update", description = "Update an existing rental by finding it with his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental updated !",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PutMapping(path = "/{id}")
    public @ResponseBody RentalsResponseDto updateRental(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam Float surface,
            @RequestParam Float price,
            @RequestParam String description
    ){
        try {
            Optional<Rental> rentalOptional = rentalRepository.findById(Long.valueOf(id));
                if(rentalOptional.isPresent()){
                    Rental rental = rentalOptional.get();
                    if(rental.getOwner_id().equals(getAuthenticatedUser().getId())){
                        rentalService.updateRental(name, surface, price, description, rental);
                    } else {
                        return RentalsResponseDto.builder().message("You are not authorized !").build();
                    }
                }
            return RentalsResponseDto.builder().message("Rental updated !").build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    };

    @Operation(summary = "Rental list", description = "Get a list of all the rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rentals",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<RentalsListDto> getAllRentals(){
        try{
            return ResponseEntity.ok(rentalService.displayRentals());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Rental by id", description = "Get a rental by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental found by his id",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity<Optional<RentalDTO>> getRentalById(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(rentalService.displayRentalById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }
}
