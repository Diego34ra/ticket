package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Comentario> criar(@RequestParam Long ticketId,
                                            @RequestParam Long usuarioId,
                                            @ModelAttribute ComentarioInputDTO conteudo,
                                            @RequestParam("anexo") MultipartFile anexo) {
        var comentarioCriado = comentarioService.criar(ticketId,usuarioId,conteudo);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriado);
    }
}
