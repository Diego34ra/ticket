package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<Comentario> criar(@RequestParam Long ticketId, @RequestParam Long usuarioId, @ModelAttribute ComentarioInputDTO comentario){
        var comentarioCriado = comentarioService.criar(ticketId,usuarioId,comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriado);
    }
}
