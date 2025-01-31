package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.registroTrabalho.RegistroTrabalhoReponseDTO;

import java.util.List;

public interface RegistroTrabalhoService {

    RegistroTrabalhoReponseDTO criar(Long ticketId, RegistroTrabalhoRequestDTO registroTrabalhoRequestDTO);

    List<RegistroTrabalhoReponseDTO> buscarTodosPorTicket(Long ticketId);

    RegistroTrabalhoReponseDTO atualizar(Long registroId, RegistroTrabalhoRequestUpdateDTO registroTrabalhoRequestUpdateDTO);

    void deletarPorId(Long registroId);
}
