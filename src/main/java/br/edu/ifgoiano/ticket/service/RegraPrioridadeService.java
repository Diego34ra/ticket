package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;

import java.util.Optional;

public interface RegraPrioridadeService {
    RegraPrioridade findByCategoriaAndDepartamento(Categoria categoria, Departamento departamento);
}
