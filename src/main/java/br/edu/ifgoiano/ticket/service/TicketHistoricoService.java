package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.model.TicketHistorico;

public interface TicketHistoricoService {
    TicketHistorico criar(TicketHistorico ticketHistorico);

    void deletarPorId(Long id);
}
