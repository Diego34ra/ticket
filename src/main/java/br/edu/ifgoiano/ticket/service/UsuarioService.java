package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.MessageResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioOutputDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsuarioService {

    ResponseEntity<MessageResponseDTO> criar(UsuarioInputDTO usuarioInputDTO);

    List<UsuarioOutputDTO> buscarTodos();

    UsuarioOutputDTO buscaPorId(Long uuid);

    UsuarioOutputDTO atualizar(Long uuid, UsuarioInputDTO usuarioInputDTO);

    void deletePorId(Long uuid);

    boolean verificarSeUsuarioEhGerente(Long id);
}
