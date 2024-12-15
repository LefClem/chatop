package com.chatop.chatop.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for rental details")
public class RentalDTO {
    @Schema(description = "ID of the rental", example = "1")
    private Integer id;
    @Schema(description = "Name of the rental", example = "Big Apartment")
    private String name;
    @Schema(description = "Price by night of the rental ", example = "100")
    private Float price;
    @Schema(description = "Surface area of the rental in square meters", example = "50")
    private Float surface;
    @Schema(description = "URL of the rental picture", example = "http://localhost:8080/uploads/picture.jpg")
    private String picture;
    @Schema(description = "Description of the rental", example = "A cozy apartment in the city center")
    private String description;
    @Schema(description = "ID of the owner", example = "1")
    private Integer owner_id;
    @Schema(description = "Creation date of the rental record", example = "2024-05-01T00:00:00.000Z")
    private Date created_at;
    @Schema(description = "Last update date of the rental record", example = "2024-05-01T00:00:00.000Z")
    private Date updated_at;

    public void setPicture(String picture){
        this.picture = "http://localhost:3001" + picture;
    }
}
