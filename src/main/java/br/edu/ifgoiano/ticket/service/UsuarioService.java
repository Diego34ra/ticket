package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioOutputDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioOutputDTO criar(UsuarioInputDTO usuarioInputDTO);

    List<UsuarioOutputDTO> buscarTodos();

    UsuarioOutputDTO buscaPorId(Long uuid);

    UsuarioOutputDTO atualizar(Long uuid, UsuarioInputDTO usuarioInputDTO);

    void deletePorId(Long uuid);

    boolean verificarSeUsuarioEhGerente(Long id);
}
