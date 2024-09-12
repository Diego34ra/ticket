package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;

import java.util.List;

public interface RegistroTrabalhoService {

    RegistroTrabalhoOutputDTO criar(Long ticketId, RegistroTrabalhoInputDTO registroTrabalhoInputDTO);

    List<RegistroTrabalhoOutputDTO> buscarTodosPorTicket(Long ticketId);

    RegistroTrabalhoOutputDTO atualizar(Long registroId, RegistroTrabalhoInputUpdateDTO registroTrabalhoInputUpdateDTO);

    void deletarPorId(Long registroId);
}
