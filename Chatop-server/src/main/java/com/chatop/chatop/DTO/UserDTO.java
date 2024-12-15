package com.chatop.chatop.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO for a user")
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private Date created_at;
    private Date updated_at;
}
