package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.MessageResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UsuarioService {

    ResponseEntity<MessageResponseDTO> criar(UsuarioRequestDTO usuarioRequestDTO);

    List<UsuarioResponseDTO> buscarTodos();

    UsuarioResponseDTO buscaPorId(Long uuid);

    UsuarioResponseDTO atualizar(Long uuid, UsuarioRequestDTO usuarioRequestDTO);

    void deletePorId(Long uuid);

    boolean verificarSeUsuarioEhGerente(Long id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
