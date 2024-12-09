package com.chatop.chatop.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO for creating a message")
public class MessageDtoResponse {
    @Schema(description = "The content of the message created", required = true, example = "This is a message")
    private String message;
}
