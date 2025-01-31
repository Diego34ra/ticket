package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.regraPrioridade.RegraPrioridadeResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;
import br.edu.ifgoiano.ticket.repository.RegraPrioridadeRepository;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import br.edu.ifgoiano.ticket.service.DepartamentoService;
import br.edu.ifgoiano.ticket.service.RegraPrioridadeService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegraPrioridadeServiceImpl implements RegraPrioridadeService {

    @Autowired
    private RegraPrioridadeRepository regraPrioridadeRepository;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Override
    @CacheEvict(value = "regraPrioridadeCache", allEntries = true)
    public RegraPrioridadeResponseDTO criar(RegraPrioridadeRequestDTO regraPrioridadeRequestDTO) {
        RegraPrioridade regraPrioridade = mapper.mapTo(regraPrioridadeRequestDTO, RegraPrioridade.class);
        Categoria categoria = categoriaService.buscaPorId(regraPrioridade.getCategoria().getId());
        Departamento departamento = mapper.mapTo(departamentoService.buscarPorId(regraPrioridade.getDepartamento().getId()),Departamento.class);
        regraPrioridade.setCategoria(categoria);
        regraPrioridade.setDepartamento(departamento);
        return mapper.mapTo(regraPrioridadeRepository.save(regraPrioridade), RegraPrioridadeResponseDTO.class);
    }

    @Override
    @Cacheable(value = "regraPrioridadeCache")
    public List<RegraPrioridadeResponseDTO> buscarTodos() {
        return mapper.toList(regraPrioridadeRepository.findAll(), RegraPrioridadeResponseDTO.class);
    }

    @Override
    public RegraPrioridade buscarPorCategoriaAndDepartamento(Categoria categoria, Departamento departamento) {
        Optional<RegraPrioridade> regraPrioridadeOptional = regraPrioridadeRepository.findByCategoriaAndDepartamento(categoria,departamento);
        return regraPrioridadeOptional.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado prioridade para a categoria e departamento."));
    }

    @Override
    @CacheEvict(value = "regraPrioridadeCache", allEntries = true)
    public RegraPrioridadeResponseDTO atualizar(Long id, RegraPrioridadeRequestDTO regraPrioridadeRequestDTO) {
        RegraPrioridade regraPrioridade = regraPrioridadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada regra de prioridade com esse id."));
        regraPrioridade.setPrioridade(regraPrioridadeRequestDTO.getPrioridade());
        return mapper.mapTo(regraPrioridadeRepository.save(regraPrioridade), RegraPrioridadeResponseDTO.class);
    }

    @Override
    @CacheEvict(value = "regraPrioridadeCache", allEntries = true)
    public void deletarPorId(Long id) {
        regraPrioridadeRepository.deleteById(id);
    }
}
