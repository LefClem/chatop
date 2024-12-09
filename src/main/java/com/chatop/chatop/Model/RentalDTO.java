package com.chatop.chatop.Model;

import com.chatop.chatop.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private Integer id;
    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private Integer owner_id;
    private Date created_at;
    private Date updated_at;

    public void setPicture(String picture){
        String baseUrl = "http://localhost:8080";
        this.picture = baseUrl + picture;
    }
}
