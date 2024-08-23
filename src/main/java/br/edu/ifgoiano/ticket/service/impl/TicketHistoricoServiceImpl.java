package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.model.TicketHistorico;
import br.edu.ifgoiano.ticket.repository.TicketHistoricoRepository;
import br.edu.ifgoiano.ticket.service.TicketHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketHistoricoServiceImpl implements TicketHistoricoService {

    @Autowired
    private TicketHistoricoRepository ticketHistoricoRepository;

    @Override
    public TicketHistorico criar(TicketHistorico ticketHistorico) {
        return ticketHistoricoRepository.save(ticketHistorico);
    }

    @Override
    public void deletarPorId(Long id) {
        ticketHistoricoRepository.deleteById(id);
    }
}
