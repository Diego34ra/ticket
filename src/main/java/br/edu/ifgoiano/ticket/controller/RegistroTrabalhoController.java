package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.service.RegistroTrabalhoService;
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
@RequestMapping("api/v1/registroTrabalho")
@Tag(name = "Registro")
public class RegistroTrabalhoController {

    @Autowired
    private RegistroTrabalhoService registroTrabalhoService;

    @PostMapping
    @Operation(summary = "Criar um registro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegistroTrabalhoOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<RegistroTrabalhoOutputDTO> criar(@RequestParam Long ticketId, @RequestBody RegistroTrabalhoInputDTO registroTrabalhoInputDTO){
        var registroCriado = registroTrabalhoService.criar(ticketId,registroTrabalhoInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os registros de um ticket")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Registros buscados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RegistroTrabalhoOutputDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<List<RegistroTrabalhoOutputDTO>> buscarTodos(@RequestParam Long ticketId){
        var registroList = registroTrabalhoService.buscarTodosPorTicket(ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(registroList);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegistroTrabalhoOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<RegistroTrabalhoOutputDTO> atualizar(@RequestParam Long id, @RequestBody RegistroTrabalhoInputUpdateDTO registroTrabalhoInputUpdateDTO){
        var registroAtualizado = registroTrabalhoService.atualizar(id,registroTrabalhoInputUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroAtualizado);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar um registro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro deletado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<?> deletarPorId(@RequestParam Long id){
        registroTrabalhoService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
