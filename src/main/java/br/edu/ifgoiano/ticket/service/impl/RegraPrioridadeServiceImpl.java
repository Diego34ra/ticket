package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;
import br.edu.ifgoiano.ticket.repository.RegraPrioridadeRepository;
import br.edu.ifgoiano.ticket.service.RegraPrioridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegraPrioridadeServiceImpl implements RegraPrioridadeService {

    @Autowired
    private RegraPrioridadeRepository regraPrioridadeRepository;

    @Override
    public RegraPrioridade findByCategoriaAndDepartamento(Categoria categoria, Departamento departamento) {
        Optional<RegraPrioridade> regraPrioridadeOptional = regraPrioridadeRepository.findByCategoriaAndDepartamento(categoria,departamento);
        return regraPrioridadeOptional.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado prioridade para a categoria e departamento."));
    }
}
