package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.ComentarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.FileResponseDownload;
import br.edu.ifgoiano.ticket.model.Comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {

    ComentarioResponseDTO criar(Long ticketId, ComentarioRequestDTO comentarioInputDTO);

    Optional<Comentario> buscarPorId(Long id);

    FileResponseDownload downloadAnexo(Long roomId, String filename);

    List<ComentarioResponseDTO> buscarPorTicketId(Long ticketId);

    ComentarioResponseDTO atualizar(Long comentarioId, ComentarioRequestUpdateDTO comentarioInputUpdateDTO);

    void deletarPorId(Long comentarioId);

    void deletarAnexoPorNome(Long comentarioId, String fileName);
}
