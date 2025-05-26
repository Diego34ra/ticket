package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.regraPrioridade.RegraPrioridadeResponseDTO;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/z")
@Tag(name = "Regra Prioridade")
public class RegraPrioridadeController {


    @Autowired
    private RegraPrioridadeService regraPrioridadeService;

    @PostMapping
    @Operation(summary = "Criar um regra de prioridade")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Regra criada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegraPrioridadeResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "regraPrioridadeCache", allEntries = true)
    public ResponseEntity<RegraPrioridadeResponseDTO> criar(@RequestBody RegraPrioridadeRequestDTO regraPrioridadeRequestDTO){
        var regraPrioridadeCriada = regraPrioridadeService.criar(regraPrioridadeRequestDTO);
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
                            array = @ArraySchema(schema = @Schema(implementation = RegraPrioridadeResponseDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @Cacheable(value = "regraPrioridadeCache")
    public ResponseEntity<List<RegraPrioridadeResponseDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(regraPrioridadeService.buscarTodos());
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma regra de prioridade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Regra atualizada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegraPrioridadeResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "regraPrioridadeCache", allEntries = true)
    public ResponseEntity<RegraPrioridadeResponseDTO> atualizar(@PathVariable Long id, @RequestBody RegraPrioridadeRequestDTO regraPrioridadeRequestDTO){
        var regraPrioridadeAtualizada = regraPrioridadeService.atualizar(id, regraPrioridadeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(regraPrioridadeAtualizada);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar uma regra de prioridade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Regra deletada com sucesso."),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "regraPrioridadeCache", allEntries = true)
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        regraPrioridadeService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
