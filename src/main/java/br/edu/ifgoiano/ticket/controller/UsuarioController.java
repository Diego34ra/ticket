package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.UsuarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/ticket/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    private ResponseEntity<UsuarioOutputDTO> criar(@RequestBody UsuarioInputDTO usuario){
        var usuarioCriado = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping
    private ResponseEntity<List<UsuarioOutputDTO>> buscarTodos(){
        var usuarios = usuarioService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("{id}")
    private ResponseEntity<UsuarioOutputDTO> buscarPorId(@PathVariable String id){
        var usuario = usuarioService.buscaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }
}
