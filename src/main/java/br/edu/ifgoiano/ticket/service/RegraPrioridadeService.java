package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.RegraPrioridadeInputDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;

import java.util.List;
import java.util.Optional;

public interface RegraPrioridadeService {

    RegraPrioridade criar(RegraPrioridadeInputDTO regraPrioridadeInputDTO);

    List<RegraPrioridade> buscarTodos();

    RegraPrioridade buscarPorCategoriaAndDepartamento(Categoria categoria, Departamento departamento);

    RegraPrioridade atualizar(Long id, RegraPrioridadeInputDTO regraPrioridadeInputDTO);

    void deletarPorId(Long id);
}
