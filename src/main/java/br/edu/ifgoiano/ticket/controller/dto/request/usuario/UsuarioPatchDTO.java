package br.edu.ifgoiano.ticket.controller.dto.request.usuario;

import br.edu.ifgoiano.ticket.model.enums.UsuarioRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioPatchDTO {

    UsuarioRole tipoUsuario;

}
