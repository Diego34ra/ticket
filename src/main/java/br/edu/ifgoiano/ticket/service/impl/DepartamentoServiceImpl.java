package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.repository.DepartamentoRepository;
import br.edu.ifgoiano.ticket.service.DepartamentoService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Override
    public Departamento criar(DepartamentoDTO departamentoDTO) {
        Departamento departamento = mapper.mapTo(departamentoDTO, Departamento.class);
        return departamentoRepository.save(departamento);
    }

    @Override
    public List<Departamento> buscarTodos() {
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum departamento com esse id."));
    }

    @Override
    public Departamento atualizar(Long id, DepartamentoDTO departamentoUpdate) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum departamento com esse id."));
        BeanUtils.copyProperties(departamentoUpdate,departamento,objectUtils.getNullPropertyNames(departamentoUpdate));
        return departamentoRepository.save(departamento);
    }

    @Override
    public void deletePorId(Long id) {
        departamentoRepository.deleteById(id);
    }
}
