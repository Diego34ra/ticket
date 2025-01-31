package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioSimpleResponseDTO;
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

    private DepartamentoRequestDTO departamentoRequestDTO;
    private UsuarioResponseDTO usuarioResponseDTO;
    private Departamento departamento;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNome("João");

        departamentoRequestDTO = new DepartamentoRequestDTO();
        departamentoRequestDTO.setNome("Financeiro");

        UsuarioSimpleResponseDTO usuarioSimpleResponseDTO = new UsuarioSimpleResponseDTO();
        usuarioSimpleResponseDTO.setId(1L);
        usuarioSimpleResponseDTO.setNome("João");

        departamentoRequestDTO.setGerente(usuarioSimpleResponseDTO);

        departamento = new Departamento();
        departamento.setNome("Financeiro");
        departamento.setGerente(usuario);
    }

    @Test
    void criar() {
        UsuarioSimpleResponseDTO usuarioSimpleResponseDTO = new UsuarioSimpleResponseDTO();
        usuarioSimpleResponseDTO.setId(1L);
        usuarioSimpleResponseDTO.setNome("João");

        DepartamentoRequestDTO departamentoRequestDTO = new DepartamentoRequestDTO();
        departamentoRequestDTO.setNome("Financeiro");
        departamentoRequestDTO.setGerente(usuarioSimpleResponseDTO);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNome("João");

        Departamento departamento = new Departamento();
        departamento.setNome("Financeiro");
        departamento.setGerente(usuario);

        DepartamentoResponseDTO departamentoResponseDTO = new DepartamentoResponseDTO();
        departamentoResponseDTO.setNome("Financeiro");
        departamentoResponseDTO.setGerente(usuarioSimpleResponseDTO);

        when(mapper.mapTo(departamentoRequestDTO, Departamento.class)).thenReturn(departamento);
        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioResponseDTO);
        when(usuarioService.verificarSeUsuarioEhGerente(1L)).thenReturn(false);
        when(mapper.mapTo(usuarioResponseDTO, Usuario.class)).thenReturn(usuario);
        when(departamentoRepository.save(departamento)).thenReturn(departamento);
        when(mapper.mapTo(departamento, DepartamentoResponseDTO.class)).thenReturn(departamentoResponseDTO);

        DepartamentoResponseDTO resultado = departamentoService.criar(departamentoRequestDTO);

        assertNotNull(resultado);
        assertEquals("Financeiro", resultado.getNome());
        assertEquals("João", resultado.getGerente().getNome());

        verify(mapper).mapTo(departamentoRequestDTO, Departamento.class);
        verify(usuarioService).buscaPorId(1L);
        verify(usuarioService).verificarSeUsuarioEhGerente(1L);
        verify(departamentoRepository).save(departamento);
        verify(mapper).mapTo(departamento, DepartamentoResponseDTO.class);
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoForGerente() {
        when(mapper.mapTo(departamentoRequestDTO, Departamento.class)).thenReturn(departamento);

        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioResponseDTO);

        when(usuarioService.verificarSeUsuarioEhGerente(1L)).thenReturn(true);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            departamentoService.criar(departamentoRequestDTO);
        });

        assertEquals("Usuário enviado não é um gerente.", exception.getMessage());

        verify(mapper).mapTo(departamentoRequestDTO, Departamento.class);
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

        DepartamentoResponseDTO departamentoResponseDTO1 = new DepartamentoResponseDTO();
        departamentoResponseDTO1.setNome("Financeiro");

        DepartamentoResponseDTO departamentoResponseDTO2 = new DepartamentoResponseDTO();
        departamentoResponseDTO2.setNome("Recursos Humanos");

        when(departamentoRepository.findAll()).thenReturn(departamentos);
        when(mapper.toList(departamentos, DepartamentoResponseDTO.class)).thenReturn(Arrays.asList(departamentoResponseDTO1, departamentoResponseDTO2));

        List<DepartamentoResponseDTO> resultado = departamentoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Financeiro", resultado.get(0).getNome());
        assertEquals("Recursos Humanos", resultado.get(1).getNome());

        verify(departamentoRepository).findAll();
        verify(mapper).toList(departamentos, DepartamentoResponseDTO.class);
    }

    @Test
    void buscarPorId() {
        Departamento departamento = new Departamento();
        departamento.setNome("Financeiro");
        departamento.setId(1L);

        DepartamentoResponseDTO departamentoResponseDTO = new DepartamentoResponseDTO();
        departamentoResponseDTO.setNome("Financeiro");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(mapper.mapTo(departamento, DepartamentoResponseDTO.class)).thenReturn(departamentoResponseDTO);

        DepartamentoResponseDTO resultado = departamentoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Financeiro", resultado.getNome());

        verify(departamentoRepository).findById(1L);
        verify(mapper).mapTo(departamento, DepartamentoResponseDTO.class);
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

        UsuarioSimpleResponseDTO usuarioUpdate = new UsuarioSimpleResponseDTO();
        usuarioUpdate.setId(1L);
        usuarioUpdate.setNome("João");

        DepartamentoRequestDTO departamentoUpdate = new DepartamentoRequestDTO();
        departamentoUpdate.setNome("Vendas Atualizado");
        departamentoUpdate.setGerente(usuarioUpdate);

        Departamento departamentoAtualizado = new Departamento();
        departamentoExistente.setId(1L);
        departamentoExistente.setNome("Vendas Atualizado");

        DepartamentoResponseDTO departamentoRetorno = new DepartamentoResponseDTO();
        departamentoRetorno.setId(1L);
        departamentoRetorno.setNome("Vendas Atualizado");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNome("João");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamentoExistente));
        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioResponseDTO);
        when(usuarioService.verificarSeUsuarioEhGerente(1L)).thenReturn(false);
        when(objectUtils.getNullPropertyNames(departamentoUpdate)).thenReturn(new String[]{});
        when(departamentoRepository.save(departamentoExistente)).thenReturn(departamentoAtualizado);
        when(mapper.mapTo(usuarioResponseDTO,Usuario.class)).thenReturn(usuario);
        when(mapper.mapTo(departamentoAtualizado, DepartamentoResponseDTO.class)).thenReturn(departamentoRetorno);

        DepartamentoResponseDTO resultado = departamentoService.atualizar(1L,departamentoUpdate);

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