package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioOutputDTO;

import java.util.List;

public interface ComentarioService {

    ComentarioOutputDTO criar(Long ticketId, Long usuarioId, ComentarioInputDTO comentarioInputDTO);

    List<ComentarioOutputDTO> buscarPorTicketId(Long ticketId);

    void deletarPorId(Long comentarioId);
}
