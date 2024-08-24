package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.model.RegistroTrabalho;

public interface RegistroTrabalhoService {

    RegistroTrabalhoOutputDTO criar(Long ticketId, RegistroTrabalhoInputDTO registroTrabalhoInputDTO);
}
