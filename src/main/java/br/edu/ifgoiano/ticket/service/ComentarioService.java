package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {

    ComentarioOutputDTO criar(Long ticketId, Long usuarioId, ComentarioRequestDTO comentarioInputDTO);

    Optional<Comentario> buscarPorId(Long id);

    List<ComentarioOutputDTO> buscarPorTicketId(Long ticketId);

    ComentarioOutputDTO atualizar(Long comentarioId, ComentarioRequestUpdateDTO comentarioInputUpdateDTO);

    void deletarPorId(Long comentarioId);

    void deletarAnexoPorNome(Long comentarioId, String fileName);
}
