package br.edu.ifgoiano.ticket.controller.dto.response.comentario;

import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioSimpleOutputDTO;
import br.edu.ifgoiano.ticket.model.FileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioTicketOutputDTO {
    private Long id;
    private String conteudo;
    private List<FileResponse> anexos;
    private UsuarioSimpleOutputDTO autor;
}
