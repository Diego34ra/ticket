package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.regraPrioridade.RegraPrioridadeResponseDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;

import java.util.List;

public interface RegraPrioridadeService {

    RegraPrioridadeResponseDTO criar(RegraPrioridadeRequestDTO regraPrioridadeRequestDTO);

    List<RegraPrioridadeResponseDTO> buscarTodos();

    RegraPrioridade buscarPorCategoriaAndDepartamento(Categoria categoria, Departamento departamento);

    RegraPrioridadeResponseDTO atualizar(Long id, RegraPrioridadeRequestDTO regraPrioridadeRequestDTO);

    void deletarPorId(Long id);
}
