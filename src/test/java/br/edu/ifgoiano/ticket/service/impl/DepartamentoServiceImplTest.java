package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.DepartamentoRepository;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceImplTest {

    @InjectMocks
    private DepartamentoServiceImpl departamentoService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private MyModelMapper mapper;

    @Mock
    private ObjectUtils objectUtils;

    private DepartamentoInputDTO departamentoInputDTO;
    private UsuarioOutputDTO usuarioOutputDTO;
    private Departamento departamento;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        usuarioOutputDTO = new UsuarioOutputDTO();
        usuarioOutputDTO.setId(1L);
        usuarioOutputDTO.setNome("João");

        departamentoInputDTO = new DepartamentoInputDTO();
        departamentoInputDTO.setNome("Financeiro");

        UsuarioSimpleOutputDTO usuarioSimpleOutputDTO = new UsuarioSimpleOutputDTO();
        usuarioSimpleOutputDTO.setId(1L);
        usuarioSimpleOutputDTO.setNome("João");

        departamentoInputDTO.setGerente(usuarioSimpleOutputDTO);

        departamento = new Departamento();
        departamento.setNome("Financeiro");
        departamento.setGerente(usuario);
    }

    @Test
    void criar() {
        UsuarioSimpleOutputDTO usuarioSimpleOutputDTO = new UsuarioSimpleOutputDTO();
        usuarioSimpleOutputDTO.setId(1L);
        usuarioSimpleOutputDTO.setNome("João");

        DepartamentoInputDTO departamentoInputDTO = new DepartamentoInputDTO();
        departamentoInputDTO.setNome("Financeiro");
        departamentoInputDTO.setGerente(usuarioSimpleOutputDTO);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        UsuarioOutputDTO usuarioOutputDTO = new UsuarioOutputDTO();
        usuarioOutputDTO.setId(1L);
        usuarioOutputDTO.setNome("João");

        Departamento departamento = new Departamento();
        departamento.setNome("Financeiro");
        departamento.setGerente(usuario);

        DepartamentoOutputDTO departamentoOutputDTO = new DepartamentoOutputDTO();
        departamentoOutputDTO.setNome("Financeiro");
        departamentoOutputDTO.setGerente(usuarioSimpleOutputDTO);

        when(mapper.mapTo(departamentoInputDTO, Departamento.class)).thenReturn(departamento);
        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioOutputDTO);
        when(usuarioService.verificarSeUsuarioEhGerente(1L)).thenReturn(false);
        when(mapper.mapTo(usuarioOutputDTO, Usuario.class)).thenReturn(usuario);
        when(departamentoRepository.save(departamento)).thenReturn(departamento);
        when(mapper.mapTo(departamento, DepartamentoOutputDTO.class)).thenReturn(departamentoOutputDTO);

        DepartamentoOutputDTO resultado = departamentoService.criar(departamentoInputDTO);

        assertNotNull(resultado);
        assertEquals("Financeiro", resultado.getNome());
        assertEquals("João", resultado.getGerente().getNome());

        verify(mapper).mapTo(departamentoInputDTO, Departamento.class);
        verify(usuarioService).buscaPorId(1L);
        verify(usuarioService).verificarSeUsuarioEhGerente(1L);
        verify(departamentoRepository).save(departamento);
        verify(mapper).mapTo(departamento, DepartamentoOutputDTO.class);
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoForGerente() {
        when(mapper.mapTo(departamentoInputDTO, Departamento.class)).thenReturn(departamento);

        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioOutputDTO);

        when(usuarioService.verificarSeUsuarioEhGerente(1L)).thenReturn(true);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            departamentoService.criar(departamentoInputDTO);
        });

        assertEquals("Usuário enviado não é um gerente.", exception.getMessage());

        verify(mapper).mapTo(departamentoInputDTO, Departamento.class);
        verify(usuarioService).buscaPorId(1L);
        verify(usuarioService).verificarSeUsuarioEhGerente(1L);

        verifyNoInteractions(departamentoRepository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void buscarTodos() {
        Departamento departamento1 = new Departamento();
        departamento1.setNome("Financeiro");

        Departamento departamento2 = new Departamento();
        departamento2.setNome("Recursos Humanos");

        List<Departamento> departamentos = Arrays.asList(departamento1, departamento2);

        DepartamentoOutputDTO departamentoOutputDTO1 = new DepartamentoOutputDTO();
        departamentoOutputDTO1.setNome("Financeiro");

        DepartamentoOutputDTO departamentoOutputDTO2 = new DepartamentoOutputDTO();
        departamentoOutputDTO2.setNome("Recursos Humanos");

        when(departamentoRepository.findAll()).thenReturn(departamentos);
        when(mapper.toList(departamentos, DepartamentoOutputDTO.class)).thenReturn(Arrays.asList(departamentoOutputDTO1, departamentoOutputDTO2));

        List<DepartamentoOutputDTO> resultado = departamentoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Financeiro", resultado.get(0).getNome());
        assertEquals("Recursos Humanos", resultado.get(1).getNome());

        verify(departamentoRepository).findAll();
        verify(mapper).toList(departamentos, DepartamentoOutputDTO.class);
    }

    @Test
    void buscarPorId() {
        Departamento departamento = new Departamento();
        departamento.setNome("Financeiro");
        departamento.setId(1L);

        DepartamentoOutputDTO departamentoOutputDTO = new DepartamentoOutputDTO();
        departamentoOutputDTO.setNome("Financeiro");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(mapper.mapTo(departamento, DepartamentoOutputDTO.class)).thenReturn(departamentoOutputDTO);

        DepartamentoOutputDTO resultado = departamentoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Financeiro", resultado.getNome());

        verify(departamentoRepository).findById(1L);
        verify(mapper).mapTo(departamento, DepartamentoOutputDTO.class);
    }

    @Test
    void deveLancarExcecaoSeDepartamentoNaoForEncontrado() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> departamentoService.buscarPorId(1L));

        verify(departamentoRepository).findById(1L);
    }

    @Test
    void atualizar() {
        Departamento departamentoExistente = new Departamento();
        departamentoExistente.setId(1L);
        departamentoExistente.setNome("Vendas");

        UsuarioSimpleOutputDTO usuarioUpdate = new UsuarioSimpleOutputDTO();
        usuarioUpdate.setId(1L);
        usuarioUpdate.setNome("João");

        DepartamentoInputDTO departamentoUpdate = new DepartamentoInputDTO();
        departamentoUpdate.setNome("Vendas Atualizado");
        departamentoUpdate.setGerente(usuarioUpdate);

        Departamento departamentoAtualizado = new Departamento();
        departamentoExistente.setId(1L);
        departamentoExistente.setNome("Vendas Atualizado");

        DepartamentoOutputDTO departamentoRetorno = new DepartamentoOutputDTO();
        departamentoRetorno.setId(1L);
        departamentoRetorno.setNome("Vendas Atualizado");

        UsuarioOutputDTO usuarioOutputDTO = new UsuarioOutputDTO();
        usuarioOutputDTO.setId(1L);
        usuarioOutputDTO.setNome("João");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamentoExistente));
        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioOutputDTO);
        when(usuarioService.verificarSeUsuarioEhGerente(1L)).thenReturn(false);
        when(objectUtils.getNullPropertyNames(departamentoUpdate)).thenReturn(new String[]{});
        when(departamentoRepository.save(departamentoExistente)).thenReturn(departamentoAtualizado);
        when(mapper.mapTo(usuarioOutputDTO,Usuario.class)).thenReturn(usuario);
        when(mapper.mapTo(departamentoAtualizado,DepartamentoOutputDTO.class)).thenReturn(departamentoRetorno);

        DepartamentoOutputDTO resultado = departamentoService.atualizar(1L,departamentoUpdate);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Vendas Atualizado", resultado.getNome());

        verify(departamentoRepository).findById(1L);
        verify(usuarioService).buscaPorId(1L);
        verify(objectUtils).getNullPropertyNames(departamentoUpdate);
        verify(departamentoRepository).save(departamentoExistente);
    }

    @Test
    void deletePorId() {
        Long id = 1L;

        departamentoService.deletePorId(id);

        verify(departamentoRepository).deleteById(id);
    }
}