package br.edu.ifgoiano.ticket.controller.dto.request;

import br.edu.ifgoiano.ticket.model.FileResponse;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private TicketComentarioOutputDTO ticket;
}