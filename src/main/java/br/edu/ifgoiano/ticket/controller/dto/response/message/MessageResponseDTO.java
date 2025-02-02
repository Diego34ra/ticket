package br.edu.ifgoiano.ticket.controller.dto.response.message;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
    private String status;
    private int code;
    private String message;
}
