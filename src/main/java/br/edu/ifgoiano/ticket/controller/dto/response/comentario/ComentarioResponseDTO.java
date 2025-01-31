package br.edu.ifgoiano.ticket.controller.dto.response.comentario;

import br.edu.ifgoiano.ticket.controller.dto.request.AnexoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketSimpleResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioSimpleResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComentarioResponseDTO {
    private Long id;
    private String conteudo;
    private List<AnexoOutputDTO> anexos;
    private UsuarioSimpleResponseDTO autor;
    private TicketSimpleResponseDTO ticket;
}
