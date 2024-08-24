package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.service.RegistroTrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registroTrabalho")
public class RegistroTrabalhoController {

    @Autowired
    private RegistroTrabalhoService registroTrabalhoService;

    @PostMapping
    public ResponseEntity<RegistroTrabalhoOutputDTO> criar(@RequestParam Long ticketId, @RequestBody RegistroTrabalhoInputDTO registroTrabalhoInputDTO){
        var registroCriado = registroTrabalhoService.criar(ticketId,registroTrabalhoInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroCriado);
    }
}
