package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.DepartamentoRepository;
import br.edu.ifgoiano.ticket.service.DepartamentoService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Override
    @CacheEvict(value = "departamentoCache", allEntries = true)
    public DepartamentoOutputDTO criar(DepartamentoInputDTO departamentoInputDTO) {
        Departamento departamento = mapper.mapTo(departamentoInputDTO, Departamento.class);
        UsuarioOutputDTO usuarioOutputDTO = usuarioService.buscaPorId(departamento.getGerente().getId());
        if(usuarioService.verificarSeUsuarioEhGerente(usuarioOutputDTO.getId()))
            throw new ResourceNotFoundException("Usuário enviado não é um gerente.");

        Usuario usuario = mapper.mapTo(usuarioOutputDTO,Usuario.class);
        departamento.setGerente(usuario);
        return mapper.mapTo(departamentoRepository.save(departamento), DepartamentoOutputDTO.class);
    }

    @Override
    @Cacheable(value = "departamentoCache")
    public List<DepartamentoOutputDTO> buscarTodos() {
        return mapper.toList(departamentoRepository.findAll(), DepartamentoOutputDTO.class);
    }

    @Override
    public DepartamentoOutputDTO buscarPorId(Long id) {
        return mapper.mapTo(departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum departamento com esse id.")), DepartamentoOutputDTO.class);
    }

    @Override
    @CacheEvict(value = "departamentoCache", allEntries = true)
    public DepartamentoOutputDTO atualizar(Long id, DepartamentoInputDTO departamentoUpdate) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum departamento com esse id."));
        UsuarioOutputDTO usuarioOutputDTO = usuarioService.buscaPorId(departamentoUpdate.getGerente().getId());
        if(usuarioService.verificarSeUsuarioEhGerente(usuarioOutputDTO.getId()))
            throw new ResourceNotFoundException("Usuário enviado não é um gerente.");
        BeanUtils.copyProperties(departamentoUpdate,departamento,objectUtils.getNullPropertyNames(departamentoUpdate));
        Usuario usuario = mapper.mapTo(usuarioOutputDTO,Usuario.class);
        departamento.setGerente(usuario);
        return mapper.mapTo(departamentoRepository.save(departamento), DepartamentoOutputDTO.class);
    }

    @Override
    @CacheEvict(value = "departamentoCache", allEntries = true)
    public void deletePorId(Long id) {
        departamentoRepository.deleteById(id);
    }
}
