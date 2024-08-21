package br.edu.ifgoiano.ticket.controller.dto.request.usuario;

import br.edu.ifgoiano.ticket.controller.dto.request.TelefoneDTO;
import br.edu.ifgoiano.ticket.model.Telefone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioInputDTO {

    private String nome;

    private String email;

    private String cpf;

    private String senha;

    private List<TelefoneDTO> contatos;
}
