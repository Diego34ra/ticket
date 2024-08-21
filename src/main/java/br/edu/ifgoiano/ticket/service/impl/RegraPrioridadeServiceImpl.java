package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.RegraPrioridadeInputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;
import br.edu.ifgoiano.ticket.repository.RegraPrioridadeRepository;
import br.edu.ifgoiano.ticket.service.RegraPrioridadeService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegraPrioridadeServiceImpl implements RegraPrioridadeService {

    @Autowired
    private RegraPrioridadeRepository regraPrioridadeRepository;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Override
    public RegraPrioridade criar(RegraPrioridadeInputDTO regraPrioridadeInputDTO) {
        RegraPrioridade regraPrioridade = mapper.mapTo(regraPrioridadeInputDTO, RegraPrioridade.class);
        return regraPrioridadeRepository.save(regraPrioridade);
    }

    @Override
    public List<RegraPrioridade> buscarTodos() {
        return regraPrioridadeRepository.findAll();
    }

    @Override
    public RegraPrioridade buscarPorCategoriaAndDepartamento(Categoria categoria, Departamento departamento) {
        Optional<RegraPrioridade> regraPrioridadeOptional = regraPrioridadeRepository.findByCategoriaAndDepartamento(categoria,departamento);
        return regraPrioridadeOptional.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado prioridade para a categoria e departamento."));
    }

    @Override
    public RegraPrioridade atualizar(Long id, RegraPrioridadeInputDTO regraPrioridadeInputDTO) {
        RegraPrioridade regraPrioridadeUpdate = mapper.mapTo(regraPrioridadeInputDTO,RegraPrioridade.class);

        return null;
    }

    @Override
    public void deletarPorId(Long id) {

    }
}
