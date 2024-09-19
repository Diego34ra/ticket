package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.service.RegistroTrabalhoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registroTrabalho")
@Tag(name = "Registro")
public class RegistroTrabalhoController {

    @Autowired
    private RegistroTrabalhoService registroTrabalhoService;

    @PostMapping
    public ResponseEntity<RegistroTrabalhoOutputDTO> criar(@RequestParam Long ticketId, @RequestBody RegistroTrabalhoInputDTO registroTrabalhoInputDTO){
        var registroCriado = registroTrabalhoService.criar(ticketId,registroTrabalhoInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroCriado);
    }

    @PutMapping("{id}")
    public ResponseEntity<RegistroTrabalhoOutputDTO> atualizar(@RequestParam Long registroId, @RequestBody RegistroTrabalhoInputUpdateDTO registroTrabalhoInputUpdateDTO){
        var registroAtualizado = registroTrabalhoService.atualizar(registroId,registroTrabalhoInputUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarPorId(@RequestParam Long registroId){
        registroTrabalhoService.deletarPorId(registroId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
