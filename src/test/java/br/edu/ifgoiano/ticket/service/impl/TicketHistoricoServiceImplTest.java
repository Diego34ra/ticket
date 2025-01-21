package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.model.TicketHistorico;
import br.edu.ifgoiano.ticket.repository.TicketHistoricoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketHistoricoServiceImplTest {

    @InjectMocks
    private TicketHistoricoServiceImpl service;

    @Mock
    private TicketHistoricoRepository repository;

    @Test
    void criar() {
        TicketHistorico ticketHistorico = new TicketHistorico();
        TicketHistorico ticketSalvo = new TicketHistorico();

        when(repository.save(ticketHistorico)).thenReturn(ticketSalvo);

        TicketHistorico result = service.criar(ticketHistorico);

        assertNotNull(result);
        verify(repository).save(ticketHistorico);
    }

    @Test
    void deletarPorId() {
        Long id = 1L;

        service.deletarPorId(id);

        verify(repository).deleteById(id);
    }
}