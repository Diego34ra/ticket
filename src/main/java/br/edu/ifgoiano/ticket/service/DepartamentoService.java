package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoDTO;
import br.edu.ifgoiano.ticket.model.Departamento;

import java.util.List;

public interface DepartamentoService {

    Departamento criar(DepartamentoDTO departamento);

    List<Departamento> buscarTodos();

    Departamento buscaPorId(Long id);

    Departamento atualizar(Long id,DepartamentoDTO departamento);

    void deletePorId(Long id);
}
