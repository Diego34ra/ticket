package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.repository.RegraPrioridadeRepository;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import br.edu.ifgoiano.ticket.service.DepartamentoService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class RegraPrioridadeServiceImplTest {

    @InjectMocks
    private RegraPrioridadeServiceImpl service;

    @Mock
    private RegraPrioridadeRepository regraPrioridadeRepository;

    @Mock
    private DepartamentoService departamentoService;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private MyModelMapper mapper;

    @Mock
    private ObjectUtils objectUtils;


    @Test
    void criar() {
        RegraPrioridadeInputDTO inputDTO = new RegraPrioridadeInputDTO();
        inputDTO.setPrioridade(Prioridade.ALTA);

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Departamento departamento = new Departamento();
        departamento.setId(1L);

        RegraPrioridade regraPrioridade = new RegraPrioridade();
        regraPrioridade.setCategoria(categoria);
        regraPrioridade.setDepartamento(departamento);

        DepartamentoOutputDTO departamentoOutputDTO = new DepartamentoOutputDTO();
        departamentoOutputDTO.setId(1L);


        RegraPrioridadeOutputDTO outputDTO = new RegraPrioridadeOutputDTO();

        when(mapper.mapTo(inputDTO, RegraPrioridade.class)).thenReturn(regraPrioridade);
        when(categoriaService.buscaPorId(1L)).thenReturn(categoria);
        when(departamentoService.buscarPorId(1L)).thenReturn(departamentoOutputDTO);
        when(mapper.mapTo(departamentoOutputDTO, Departamento.class)).thenReturn(departamento);
        when(regraPrioridadeRepository.save(regraPrioridade)).thenReturn(regraPrioridade);
        when(mapper.mapTo(regraPrioridade, RegraPrioridadeOutputDTO.class)).thenReturn(outputDTO);

        RegraPrioridadeOutputDTO result = service.criar(inputDTO);

        assertNotNull(result);
        verify(categoriaService).buscaPorId(anyLong());
        verify(departamentoService).buscarPorId(anyLong());
        verify(regraPrioridadeRepository).save(regraPrioridade);
    }

    @Test
    void buscarTodos() {
        List<RegraPrioridade> regras = List.of(new RegraPrioridade());
        List<RegraPrioridadeOutputDTO> outputDTOs = List.of(new RegraPrioridadeOutputDTO());

        when(regraPrioridadeRepository.findAll()).thenReturn(regras);
        when(mapper.toList(regras, RegraPrioridadeOutputDTO.class)).thenReturn(outputDTOs);

        List<RegraPrioridadeOutputDTO> result = service.buscarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(regraPrioridadeRepository).findAll();
    }

    @Test
    void buscarPorCategoriaAndDepartamento() {
        Categoria categoria = new Categoria();
        Departamento departamento = new Departamento();
        RegraPrioridade regraPrioridade = new RegraPrioridade();

        when(regraPrioridadeRepository.findByCategoriaAndDepartamento(categoria, departamento))
                .thenReturn(Optional.of(regraPrioridade));

        RegraPrioridade result = service.buscarPorCategoriaAndDepartamento(categoria, departamento);

        assertNotNull(result);
        verify(regraPrioridadeRepository).findByCategoriaAndDepartamento(categoria, departamento);
    }

    @Test
    void atualizar() {
        Long id = 1L;
        RegraPrioridadeInputDTO inputDTO = new RegraPrioridadeInputDTO();
        RegraPrioridade regraPrioridade = new RegraPrioridade();
        RegraPrioridadeOutputDTO outputDTO = new RegraPrioridadeOutputDTO();

        when(regraPrioridadeRepository.findById(id)).thenReturn(Optional.of(regraPrioridade));
        when(regraPrioridadeRepository.save(regraPrioridade)).thenReturn(regraPrioridade);
        when(mapper.mapTo(regraPrioridade, RegraPrioridadeOutputDTO.class)).thenReturn(outputDTO);

        RegraPrioridadeOutputDTO result = service.atualizar(id, inputDTO);

        assertNotNull(result);
        verify(regraPrioridadeRepository).findById(id);
        verify(regraPrioridadeRepository).save(regraPrioridade);
    }

    @Test
    void atualizar_deveLancarExcecaoQuandoNaoEncontrarRegra() {
        Long id = 1L;
        RegraPrioridadeInputDTO inputDTO = new RegraPrioridadeInputDTO();

        when(regraPrioridadeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.atualizar(id, inputDTO));
        verify(regraPrioridadeRepository).findById(id);
    }

    @Test
    void deletarPorId() {
        Long id = 1L;

        service.deletarPorId(id);

        verify(regraPrioridadeRepository).deleteById(id);
    }
}