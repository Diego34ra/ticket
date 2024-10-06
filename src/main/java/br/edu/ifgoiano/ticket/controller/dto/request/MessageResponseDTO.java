package br.edu.ifgoiano.ticket.controller.dto.request;

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
