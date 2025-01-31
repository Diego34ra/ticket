package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.registroTrabalho.RegistroTrabalhoReponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.RegistroTrabalho;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.RegistroTrabalhoRepository;
import br.edu.ifgoiano.ticket.service.TicketService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroTrabalhoServiceImplTest {

    @InjectMocks
    private RegistroTrabalhoServiceImpl service;

    @Mock
    private RegistroTrabalhoRepository registroTrabalhoRepository;

    @Mock
    private TicketService ticketService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ObjectUtils objectUtils;

    @Mock
    private MyModelMapper mapper;

    @Test
    void criar() {
        Long ticketId = 1L;
        RegistroTrabalhoRequestDTO inputDTO = new RegistroTrabalhoRequestDTO();

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setId("1");
        ticketResponseDTO.setTitulo("Ticket Teste");

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitulo("Ticket Teste");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNome("Usuario Teste");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Usuario Teste");

        RegistroTrabalho registroTrabalho = new RegistroTrabalho();
        RegistroTrabalhoReponseDTO outputDTO = new RegistroTrabalhoReponseDTO();

        when(ticketService.buscarPorId(ticketId)).thenReturn(ticketResponseDTO);
        when(mapper.mapTo(ticketResponseDTO, Ticket.class)).thenReturn(ticket);

        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioResponseDTO);
        when(mapper.mapTo(usuarioResponseDTO, Usuario.class)).thenReturn(usuario);

        when(mapper.mapTo(inputDTO, RegistroTrabalho.class)).thenReturn(registroTrabalho);
        when(registroTrabalhoRepository.save(any(RegistroTrabalho.class))).thenReturn(registroTrabalho);
        when(mapper.mapTo(registroTrabalho, RegistroTrabalhoReponseDTO.class)).thenReturn(outputDTO);

        RegistroTrabalhoReponseDTO result = service.criar(ticketId, inputDTO);

        assertNotNull(result);
        verify(ticketService).buscarPorId(ticketId);
        verify(usuarioService).buscaPorId(1L);
        verify(registroTrabalhoRepository).save(registroTrabalho);
    }

    @Test
    void buscarTodosPorTicket() {
        Long ticketId = 1L;
        List<RegistroTrabalho> registros = List.of(new RegistroTrabalho(),new RegistroTrabalho());
        List<RegistroTrabalhoReponseDTO> outputDTOs = List.of(new RegistroTrabalhoReponseDTO(),new RegistroTrabalhoReponseDTO());

        when(registroTrabalhoRepository.findByTicketId(ticketId)).thenReturn(registros);
        when(mapper.toList(registros, RegistroTrabalhoReponseDTO.class)).thenReturn(outputDTOs);

        List<RegistroTrabalhoReponseDTO> result = service.buscarTodosPorTicket(ticketId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(registroTrabalhoRepository).findByTicketId(ticketId);
    }

    @Test
    void atualizar() {
        Long registroId = 1L;
        RegistroTrabalhoRequestUpdateDTO inputDTO = new RegistroTrabalhoRequestUpdateDTO();
        RegistroTrabalho registroTrabalho = new RegistroTrabalho();
        RegistroTrabalhoReponseDTO outputDTO = new RegistroTrabalhoReponseDTO();

        when(registroTrabalhoRepository.findById(registroId)).thenReturn(Optional.of(registroTrabalho));
        when(objectUtils.getNullPropertyNames(inputDTO)).thenReturn(new String[0]);
        when(registroTrabalhoRepository.save(registroTrabalho)).thenReturn(registroTrabalho);
        when(mapper.mapTo(registroTrabalho, RegistroTrabalhoReponseDTO.class)).thenReturn(outputDTO);

        RegistroTrabalhoReponseDTO result = service.atualizar(registroId, inputDTO);

        assertNotNull(result);
        verify(registroTrabalhoRepository).findById(registroId);
        verify(registroTrabalhoRepository).save(registroTrabalho);
    }

    @Test
    void atualizar_deveLancarExcecaoQuandoRegistroNaoForEncontrado() {
        Long registroId = 1L;
        RegistroTrabalhoRequestUpdateDTO inputDTO = new RegistroTrabalhoRequestUpdateDTO();

        when(registroTrabalhoRepository.findById(registroId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.atualizar(registroId, inputDTO));
        verify(registroTrabalhoRepository).findById(registroId);
        verifyNoMoreInteractions(registroTrabalhoRepository);
    }

    @Test
    void deletarPorId() {
        Long registroId = 1L;

        service.deletarPorId(registroId);

        verify(registroTrabalhoRepository).deleteById(registroId);
    }
}