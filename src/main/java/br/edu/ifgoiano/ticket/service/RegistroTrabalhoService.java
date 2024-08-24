package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;

public interface RegistroTrabalhoService {

    RegistroTrabalhoOutputDTO criar(Long ticketId, RegistroTrabalhoInputDTO registroTrabalhoInputDTO);
}
