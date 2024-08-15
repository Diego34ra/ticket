package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketOutputDTO;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;
import br.edu.ifgoiano.ticket.model.StatusTicket;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.repository.RegraPrioridadeRepository;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.service.DepartamentoService;
import br.edu.ifgoiano.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRespository ticketRespository;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private RegraPrioridadeRepository regraPrioridadeRepository;

    @Autowired
    private MyModelMapper mapper;

    @Override
    public TicketOutputDTO criar(TicketInputDTO ticketInputDTO) {
        Ticket ticket = mapper.mapTo(ticketInputDTO, Ticket.class);
        ticket.setDataCriacao(LocalDateTime.now());
        ticket.setStatus(StatusTicket.ABERTO);
        RegraPrioridade regraPrioridade = regraPrioridadeRepository.findByCategoriaAndDepartamento(ticket.getCategoria(),ticket.getDepartamento());
        return mapper.mapTo(ticketRespository.save(ticket), TicketOutputDTO.class);
    }
}
