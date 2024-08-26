package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {

    ComentarioOutputDTO criar(Long ticketId, Long usuarioId, ComentarioInputDTO comentarioInputDTO);

    Optional<Comentario> buscarPorId(Long id);

    List<ComentarioOutputDTO> buscarPorTicketId(Long ticketId);

    ComentarioOutputDTO atualizar(Long comentarioId, ComentarioInputUpdateDTO comentarioInputUpdateDTO);

    void deletarPorId(Long comentarioId);

    void deletarAnexoPorNome(Long comentarioId, String fileName);
}
