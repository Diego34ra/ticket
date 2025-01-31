package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.regraPrioridade.RegraPrioridadeResponseDTO;
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
        RegraPrioridadeRequestDTO inputDTO = new RegraPrioridadeRequestDTO();
        inputDTO.setPrioridade(Prioridade.ALTA);

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Departamento departamento = new Departamento();
        departamento.setId(1L);

        RegraPrioridade regraPrioridade = new RegraPrioridade();
        regraPrioridade.setCategoria(categoria);
        regraPrioridade.setDepartamento(departamento);

        DepartamentoResponseDTO departamentoResponseDTO = new DepartamentoResponseDTO();
        departamentoResponseDTO.setId(1L);


        RegraPrioridadeResponseDTO outputDTO = new RegraPrioridadeResponseDTO();

        when(mapper.mapTo(inputDTO, RegraPrioridade.class)).thenReturn(regraPrioridade);
        when(categoriaService.buscaPorId(1L)).thenReturn(categoria);
        when(departamentoService.buscarPorId(1L)).thenReturn(departamentoResponseDTO);
        when(mapper.mapTo(departamentoResponseDTO, Departamento.class)).thenReturn(departamento);
        when(regraPrioridadeRepository.save(regraPrioridade)).thenReturn(regraPrioridade);
        when(mapper.mapTo(regraPrioridade, RegraPrioridadeResponseDTO.class)).thenReturn(outputDTO);

        RegraPrioridadeResponseDTO result = service.criar(inputDTO);

        assertNotNull(result);
        verify(categoriaService).buscaPorId(anyLong());
        verify(departamentoService).buscarPorId(anyLong());
        verify(regraPrioridadeRepository).save(regraPrioridade);
    }

    @Test
    void buscarTodos() {
        List<RegraPrioridade> regras = List.of(new RegraPrioridade());
        List<RegraPrioridadeResponseDTO> outputDTOs = List.of(new RegraPrioridadeResponseDTO());

        when(regraPrioridadeRepository.findAll()).thenReturn(regras);
        when(mapper.toList(regras, RegraPrioridadeResponseDTO.class)).thenReturn(outputDTOs);

        List<RegraPrioridadeResponseDTO> result = service.buscarTodos();

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
        RegraPrioridadeRequestDTO inputDTO = new RegraPrioridadeRequestDTO();
        RegraPrioridade regraPrioridade = new RegraPrioridade();
        RegraPrioridadeResponseDTO outputDTO = new RegraPrioridadeResponseDTO();

        when(regraPrioridadeRepository.findById(id)).thenReturn(Optional.of(regraPrioridade));
        when(regraPrioridadeRepository.save(regraPrioridade)).thenReturn(regraPrioridade);
        when(mapper.mapTo(regraPrioridade, RegraPrioridadeResponseDTO.class)).thenReturn(outputDTO);

        RegraPrioridadeResponseDTO result = service.atualizar(id, inputDTO);

        assertNotNull(result);
        verify(regraPrioridadeRepository).findById(id);
        verify(regraPrioridadeRepository).save(regraPrioridade);
    }

    @Test
    void atualizar_deveLancarExcecaoQuandoNaoEncontrarRegra() {
        Long id = 1L;
        RegraPrioridadeRequestDTO inputDTO = new RegraPrioridadeRequestDTO();

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