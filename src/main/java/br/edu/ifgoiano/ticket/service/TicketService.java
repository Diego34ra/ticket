package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketSimpleResponseDTO;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface TicketService {
    TicketResponseDTO criar(TicketRequestDTO ticketRequestDTO);

    List<TicketSimpleResponseDTO> buscarTodos();

    List<TicketSimpleResponseDTO> buscarTodosFilter(String titulo, StatusTicket status, Prioridade prioridade, String nomeResponsavel, String dataInicio, String dataFim);

    TicketResponseDTO buscarPorId(Long id);

    TicketResponseDTO atualizar(Long id, TicketRequestUpdateDTO ticketRequestUpdateDTO);

    void deletePorId(Long id);

    ByteArrayInputStream generateCsvReportByDate(String dataInicio, String dataFim);

}
