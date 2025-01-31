package br.edu.ifgoiano.ticket.controller.dto.request.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioSimpleInputDTO {
    private Long id;
    private String nome;
    private String email;
}
