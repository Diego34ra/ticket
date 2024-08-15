package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketOutputDTO;
import br.edu.ifgoiano.ticket.model.Ticket;

public interface TicketService {
    TicketOutputDTO criar(TicketInputDTO ticket);
}
