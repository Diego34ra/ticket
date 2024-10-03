package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.EmailDTO;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.producer.TicketProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/teste")
public class TesteController {

    @Autowired
    private TicketProducer ticketProducer;

    @PostMapping
    public ResponseEntity<?> teste(@RequestBody EmailDTO emailDTO){
        ticketProducer.publishMessageEmail(emailDTO);
        return ResponseEntity.ok().build();
    }
}
