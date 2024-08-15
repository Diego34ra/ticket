package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketOutputDTO;
import br.edu.ifgoiano.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketOutputDTO> criar(@RequestBody TicketInputDTO ticket){
        var ticketCriado = ticketService.criar(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCriado);
    }
}
