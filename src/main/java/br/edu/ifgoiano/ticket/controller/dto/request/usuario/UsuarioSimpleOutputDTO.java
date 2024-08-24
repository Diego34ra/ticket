package br.edu.ifgoiano.ticket.controller.dto.request.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSimpleOutputDTO {
    private Long id;
    private String nome;

    private String email;

    private String cpf;
}
