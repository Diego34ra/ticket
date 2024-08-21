package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketSimpleOutputDTO;

import java.util.List;

public interface TicketService {
    TicketOutputDTO criar(TicketInputDTO ticketInputDTO);

    List<TicketSimpleOutputDTO> buscarTodos();

    TicketOutputDTO buscarPorId(Long id);

    TicketOutputDTO atualizar(Long id, TicketInputDTO ticketInputDTO);

    void deletePorId(Long id);

}
