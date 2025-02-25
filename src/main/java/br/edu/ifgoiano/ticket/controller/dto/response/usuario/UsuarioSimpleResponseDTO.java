package br.edu.ifgoiano.ticket.controller.dto.response.usuario;

import br.edu.ifgoiano.ticket.controller.dto.request.usuario.TelefoneDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSimpleResponseDTO {
    private Long id;
    private String nome;
    private String email;
//    private List<TelefoneDTO> contatos;
}
