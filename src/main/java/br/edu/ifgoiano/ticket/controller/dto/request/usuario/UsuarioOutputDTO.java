package br.edu.ifgoiano.ticket.controller.dto.request.usuario;

import br.edu.ifgoiano.ticket.controller.dto.request.TelefoneDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioOutputDTO {

    private Long id;

    private String nome;

    private String email;

    private String cpf;

    private List<TelefoneDTO> contatos;
}
