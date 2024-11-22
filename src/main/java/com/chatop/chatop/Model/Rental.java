package com.chatop.chatop.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="RENTALS")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private Date created_at;
    private Date updated_at;
}
