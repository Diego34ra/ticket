package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketSimpleOutputDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.service.RegraPrioridadeService;
import br.edu.ifgoiano.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<TicketSimpleOutputDTO>> buscarTodos(){
        var ticketList = ticketService.buscarTodos();
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketList);
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketOutputDTO> buscarPorId(@PathVariable Long id){
        var ticket = ticketService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    @PutMapping("{id}")
    public ResponseEntity<TicketOutputDTO> atualizar(@PathVariable Long id, @RequestBody TicketInputDTO ticketInputDTO){
        var ticketAtualizado = ticketService.atualizar(id,ticketInputDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ticketAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        ticketService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
