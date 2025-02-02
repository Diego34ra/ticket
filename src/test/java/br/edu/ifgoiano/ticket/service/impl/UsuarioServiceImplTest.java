package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.response.message.MessageResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.UsuarioRepository;
import br.edu.ifgoiano.ticket.service.EmailService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl service;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ObjectUtils objectUtils;

    @Mock
    private MyModelMapper mapper;

    @Test
    void criar() {
        Usuario usuario = new Usuario();
        usuario.setSenha("teste");

        UsuarioRequestDTO usuarioInput = new UsuarioRequestDTO();

        Usuario usuarioEsperado = new Usuario();

        when(mapper.mapTo(usuarioInput, Usuario.class)).thenReturn(usuario);
        when(usuarioRepository.findByEmail(usuarioInput.getEmail())).thenReturn(null);
        when(usuarioRepository.save(usuario)).thenReturn(usuarioEsperado);

        ResponseEntity<MessageResponseDTO> response = service.criar(usuarioInput);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(usuarioRepository).save(usuario);
        verify(emailService).enviarUsuarioCadastradoEmail(usuarioEsperado);
    }

    @Test
    void buscarTodos() {
        List<Usuario> usuarios = List.of(new Usuario());
        List<UsuarioResponseDTO> outputDTOs = List.of(new UsuarioResponseDTO());

        when(usuarioRepository.findAll()).thenReturn(usuarios);
        when(mapper.toList(usuarios, UsuarioResponseDTO.class)).thenReturn(outputDTOs);

        List<UsuarioResponseDTO> result = service.buscarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void buscaPorId() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        UsuarioResponseDTO outputDTO = new UsuarioResponseDTO();

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(mapper.mapTo(usuario, UsuarioResponseDTO.class)).thenReturn(outputDTO);

        UsuarioResponseDTO result = service.buscaPorId(id);

        assertNotNull(result);
        verify(usuarioRepository).findById(id);
    }

    @Test
    void buscaPorId_deveLancarExcecaoQuandoNaoEncontrarUsuario() {
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscaPorId(id));
        verify(usuarioRepository).findById(id);
    }

    @Test
    void atualizar() {
        Long id = 1L;
        UsuarioRequestDTO inputDTO = new UsuarioRequestDTO();
        Usuario usuario = new Usuario();
        UsuarioResponseDTO outputDTO = new UsuarioResponseDTO();

        when(mapper.mapTo(inputDTO, Usuario.class)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(mapper.mapTo(usuario, UsuarioResponseDTO.class)).thenReturn(outputDTO);

        UsuarioResponseDTO result = service.atualizar(id, inputDTO);

        assertNotNull(result);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deletePorId() {
        Long id = 1L;

        service.deletePorId(id);

        verify(usuarioRepository).deleteById(id);
    }

    @Test
    void verificarSeUsuarioEhGerente_deveRetornarFalseQuandoNaoEhGerente() {
        // Arrange
        Long id = 1L;

        when(usuarioRepository.isUsuarioGerente(id)).thenReturn(false);

        // Act
        boolean result = service.verificarSeUsuarioEhGerente(id);

        // Assert
        assertTrue(result);
        verify(usuarioRepository).isUsuarioGerente(id);
    }

    @Test
    void verificarSeUsuarioEhGerente_deveRetornarTrueQuandoEhGerente() {
        // Arrange
        Long id = 1L;

        when(usuarioRepository.isUsuarioGerente(id)).thenReturn(true);

        // Act
        boolean result = service.verificarSeUsuarioEhGerente(id);

        // Assert
        assertFalse(result);
        verify(usuarioRepository).isUsuarioGerente(id);
    }
}