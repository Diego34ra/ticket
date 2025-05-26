package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.service.DepartamentoService;
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
@RequestMapping("api/v1/departamentos")
@Tag(name = "Departamento")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping
    @Operation(summary = "Criar um departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentoResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "departamentoCache", allEntries = true)
    public ResponseEntity<DepartamentoResponseDTO> criar(@RequestBody DepartamentoRequestDTO departamentoDTO){
        var departamentoCriado = departamentoService.criar(departamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os departamentos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departamentos buscados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartamentoResponseDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @Cacheable(value = "departamentoCache")
    public ResponseEntity<List<DepartamentoResponseDTO>> buscarTodos(){
        var departamentoList = departamentoService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(departamentoList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar um departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento buscado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentoResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @Cacheable(value = "departamentoCache")
    public ResponseEntity<DepartamentoResponseDTO> buscarPorId(@PathVariable Long id){
        var departamento = departamentoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(departamento);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentoResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "departamentoCache", allEntries = true)
    public ResponseEntity<DepartamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody DepartamentoRequestDTO departamentoDTO){
        var departamentoAtualizado = departamentoService.atualizar(id,departamentoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(departamentoAtualizado);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar um departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento deletado com sucesso."),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "departamentoCache", allEntries = true)
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        departamentoService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}