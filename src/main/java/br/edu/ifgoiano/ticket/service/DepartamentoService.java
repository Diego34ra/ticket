package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoResponseDTO;

import java.util.List;

public interface DepartamentoService {

    DepartamentoResponseDTO criar(DepartamentoRequestDTO departamentoRequestDTO);

    List<DepartamentoResponseDTO> buscarTodos();

    DepartamentoResponseDTO buscarPorId(Long id);

    DepartamentoResponseDTO atualizar(Long id, DepartamentoRequestDTO departamentoRequestDTO);

    void deletePorId(Long id);
}
