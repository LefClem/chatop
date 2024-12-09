package com.chatop.chatop.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO for authentication token")

public class TokenDto {
    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private String token;
}
