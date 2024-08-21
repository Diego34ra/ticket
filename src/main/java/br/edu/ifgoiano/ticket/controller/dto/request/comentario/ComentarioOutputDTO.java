package br.edu.ifgoiano.ticket.controller.dto.request.comentario;

import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioOutputDTO;
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
public class ComentarioOutputDTO {
    private Long id;
    private String conteudo;
    private List<FileResponse> anexos;
    private UsuarioOutputDTO autor;
    private TicketSimpleOutputDTO ticket;
}
