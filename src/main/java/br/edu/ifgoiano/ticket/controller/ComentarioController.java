package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ComentarioOutputDTO> criar(@RequestParam Long ticketId,
                                                     @RequestParam Long usuarioId,
                                                     @ModelAttribute ComentarioInputDTO conteudo) {
        var comentarioCriado = comentarioService.criar(ticketId,usuarioId,conteudo);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioOutputDTO>> buscarPorTicketId(@RequestParam Long ticketId){
        var comentarioList = comentarioService.buscarPorTicketId(ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(comentarioList);
    }
}
