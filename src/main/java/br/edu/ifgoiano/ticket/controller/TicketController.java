package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
@Tag(name = "Ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @Operation(summary = "Criar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket criado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TicketOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<TicketOutputDTO> criar(@RequestBody TicketInputDTO ticket){
        var ticketCriado = ticketService.criar(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar todas os tickets")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tickets buscados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TicketSimpleOutputDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<List<TicketSimpleOutputDTO>> buscarTodos(){
        var ticketList = ticketService.buscarTodos();
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria buscada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TicketOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<TicketOutputDTO> buscarPorId(@PathVariable Long id){
        var ticket = ticketService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket atualizado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TicketOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<TicketOutputDTO> atualizar(@PathVariable Long id, @RequestBody TicketInputUpdateDTO ticketInputUpdateDTO){
        var ticketAtualizado = ticketService.atualizar(id,ticketInputUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ticketAtualizado);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket deletado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        ticketService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
