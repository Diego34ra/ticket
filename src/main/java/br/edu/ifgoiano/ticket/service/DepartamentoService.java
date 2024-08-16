package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoDTO;
import br.edu.ifgoiano.ticket.model.Departamento;

import java.util.List;

public interface DepartamentoService {

    Departamento criar(DepartamentoDTO departamentoDTO);

    List<Departamento> buscarTodos();

    Departamento buscarPorId(Long id);

    Departamento atualizar(Long id,DepartamentoDTO departamentoDTO);

    void deletePorId(Long id);
}
