package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoOutputDTO;

import java.util.List;

public interface DepartamentoService {

    DepartamentoOutputDTO criar(DepartamentoInputDTO departamentoDTO);

    List<DepartamentoOutputDTO> buscarTodos();

    DepartamentoOutputDTO buscarPorId(Long id);

    DepartamentoOutputDTO atualizar(Long id, DepartamentoInputDTO departamentoDTO);

    void deletePorId(Long id);
}
