package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.service.RegraPrioridadeService;
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
@RequestMapping("api/v1/regraPrioridades")
@Tag(name = "Regra Prioridade")
public class RegraPrioridadeController {


    @Autowired
    private RegraPrioridadeService regraPrioridadeService;

    @PostMapping
    @Operation(summary = "Criar um regra de prioridade")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Regra criada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegraPrioridadeOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<RegraPrioridadeOutputDTO> criar(@RequestBody RegraPrioridadeInputDTO regraPrioridadeInputDTO){
        var regraPrioridadeCriada = regraPrioridadeService.criar(regraPrioridadeInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regraPrioridadeCriada);
    }

    @GetMapping
    @Operation(summary = "Buscar todas as regras de prioridade")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Regras buscadas com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RegraPrioridadeOutputDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<List<RegraPrioridadeOutputDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(regraPrioridadeService.buscarTodos());
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma regra de prioridade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Regra atualizada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegraPrioridadeOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<RegraPrioridadeOutputDTO> atualizar(@PathVariable Long id, @RequestBody RegraPrioridadeInputDTO regraPrioridadeInputDTO){
        var regraPrioridadeAtualizada = regraPrioridadeService.atualizar(id,regraPrioridadeInputDTO);
        return ResponseEntity.status(HttpStatus.OK).body(regraPrioridadeAtualizada);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar uma regra de prioridade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Regra deletada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        regraPrioridadeService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
