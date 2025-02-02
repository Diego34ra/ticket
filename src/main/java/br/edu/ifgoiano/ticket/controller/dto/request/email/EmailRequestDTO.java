package br.edu.ifgoiano.ticket.controller.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDTO {
    private String emailTo;
    private String assunto;
    private String texto;
}
