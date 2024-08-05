package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.UsuarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.UsuarioOutputDTO;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {

    UsuarioOutputDTO criar(UsuarioInputDTO usuario);

    List<UsuarioOutputDTO> buscarTodos();

    UsuarioOutputDTO buscaPorId(String uuid);
}
