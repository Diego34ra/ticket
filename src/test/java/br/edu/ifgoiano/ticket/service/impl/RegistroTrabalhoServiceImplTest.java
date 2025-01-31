package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioOutputDTO;
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
        RegistroTrabalhoInputDTO inputDTO = new RegistroTrabalhoInputDTO();

        TicketOutputDTO ticketOutputDTO = new TicketOutputDTO();
        ticketOutputDTO.setId("1");
        ticketOutputDTO.setTitulo("Ticket Teste");

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitulo("Ticket Teste");

        UsuarioOutputDTO usuarioOutputDTO = new UsuarioOutputDTO();
        usuarioOutputDTO.setId(1L);
        usuarioOutputDTO.setNome("Usuario Teste");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Usuario Teste");

        RegistroTrabalho registroTrabalho = new RegistroTrabalho();
        RegistroTrabalhoOutputDTO outputDTO = new RegistroTrabalhoOutputDTO();

        when(ticketService.buscarPorId(ticketId)).thenReturn(ticketOutputDTO);
        when(mapper.mapTo(ticketOutputDTO, Ticket.class)).thenReturn(ticket);

        when(usuarioService.buscaPorId(1L)).thenReturn(usuarioOutputDTO);
        when(mapper.mapTo(usuarioOutputDTO, Usuario.class)).thenReturn(usuario);

        when(mapper.mapTo(inputDTO, RegistroTrabalho.class)).thenReturn(registroTrabalho);
        when(registroTrabalhoRepository.save(any(RegistroTrabalho.class))).thenReturn(registroTrabalho);
        when(mapper.mapTo(registroTrabalho, RegistroTrabalhoOutputDTO.class)).thenReturn(outputDTO);

        RegistroTrabalhoOutputDTO result = service.criar(ticketId, inputDTO);

        assertNotNull(result);
        verify(ticketService).buscarPorId(ticketId);
        verify(usuarioService).buscaPorId(1L);
        verify(registroTrabalhoRepository).save(registroTrabalho);
    }

    @Test
    void buscarTodosPorTicket() {
        Long ticketId = 1L;
        List<RegistroTrabalho> registros = List.of(new RegistroTrabalho(),new RegistroTrabalho());
        List<RegistroTrabalhoOutputDTO> outputDTOs = List.of(new RegistroTrabalhoOutputDTO(),new RegistroTrabalhoOutputDTO());

        when(registroTrabalhoRepository.findByTicketId(ticketId)).thenReturn(registros);
        when(mapper.toList(registros, RegistroTrabalhoOutputDTO.class)).thenReturn(outputDTOs);

        List<RegistroTrabalhoOutputDTO> result = service.buscarTodosPorTicket(ticketId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(registroTrabalhoRepository).findByTicketId(ticketId);
    }

    @Test
    void atualizar() {
        Long registroId = 1L;
        RegistroTrabalhoInputUpdateDTO inputDTO = new RegistroTrabalhoInputUpdateDTO();
        RegistroTrabalho registroTrabalho = new RegistroTrabalho();
        RegistroTrabalhoOutputDTO outputDTO = new RegistroTrabalhoOutputDTO();

        when(registroTrabalhoRepository.findById(registroId)).thenReturn(Optional.of(registroTrabalho));
        when(objectUtils.getNullPropertyNames(inputDTO)).thenReturn(new String[0]);
        when(registroTrabalhoRepository.save(registroTrabalho)).thenReturn(registroTrabalho);
        when(mapper.mapTo(registroTrabalho, RegistroTrabalhoOutputDTO.class)).thenReturn(outputDTO);

        RegistroTrabalhoOutputDTO result = service.atualizar(registroId, inputDTO);

        assertNotNull(result);
        verify(registroTrabalhoRepository).findById(registroId);
        verify(registroTrabalhoRepository).save(registroTrabalho);
    }

    @Test
    void atualizar_deveLancarExcecaoQuandoRegistroNaoForEncontrado() {
        Long registroId = 1L;
        RegistroTrabalhoInputUpdateDTO inputDTO = new RegistroTrabalhoInputUpdateDTO();

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