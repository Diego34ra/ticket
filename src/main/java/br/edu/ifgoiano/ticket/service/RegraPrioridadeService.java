package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeOutputDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;

import java.util.List;

public interface RegraPrioridadeService {

    RegraPrioridadeOutputDTO criar(RegraPrioridadeInputDTO regraPrioridadeInputDTO);

    List<RegraPrioridadeOutputDTO> buscarTodos();

    RegraPrioridade buscarPorCategoriaAndDepartamento(Categoria categoria, Departamento departamento);

    RegraPrioridade atualizar(Long id, RegraPrioridadeInputDTO regraPrioridadeInputDTO);

    void deletarPorId(Long id);
}
