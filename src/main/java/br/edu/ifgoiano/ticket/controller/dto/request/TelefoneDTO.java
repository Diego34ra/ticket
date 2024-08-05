package br.edu.ifgoiano.ticket.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {
    private String ddi;
    private String ddd;
    private String numero;
}
