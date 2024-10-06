package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketSimpleOutputDTO;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;

import java.util.List;

public interface TicketService {
    TicketOutputDTO criar(TicketInputDTO ticketInputDTO);

    List<TicketSimpleOutputDTO> buscarTodos();

    List<TicketSimpleOutputDTO> buscarTodosFilter(String titulo, StatusTicket status, Prioridade prioridade, String nomeResponsavel);

    TicketOutputDTO buscarPorId(Long id);

    TicketOutputDTO atualizar(Long id, TicketInputUpdateDTO ticketInputUpdateDTO);

    void deletePorId(Long id);

}
