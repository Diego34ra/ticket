package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;

public interface ComentarioService {

    Comentario criar(Long ticketId, Long usuarioId, ComentarioInputDTO comentarioInputDTO);
}
