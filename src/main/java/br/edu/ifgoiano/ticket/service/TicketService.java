package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketSimpleOutputDTO;

import java.util.List;

public interface TicketService {
    TicketOutputDTO criar(TicketInputDTO ticketInputDTO);

    List<TicketSimpleOutputDTO> buscarTodos();

    TicketOutputDTO buscarPorId(Long id);

    TicketOutputDTO atualizar(Long id, TicketInputDTO ticketInputDTO);

    void deletePorId(Long id);

}
