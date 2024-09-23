package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoOutputDTO;
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
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentoOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<DepartamentoOutputDTO> criar(@RequestBody DepartamentoInputDTO departamentoDTO){
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
                            array = @ArraySchema(schema = @Schema(implementation = DepartamentoOutputDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<List<DepartamentoOutputDTO>> buscarTodos(){
        var departamentoList = departamentoService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(departamentoList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar um departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento buscado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentoOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<DepartamentoOutputDTO> buscarPorId(@PathVariable Long id){
        var departamento = departamentoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(departamento);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentoOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<DepartamentoOutputDTO> atualizar(@PathVariable Long id, @RequestBody DepartamentoInputDTO departamentoDTO){
        var departamentoAtualizado = departamentoService.atualizar(id,departamentoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(departamentoAtualizado);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar um departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento deletado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        departamentoService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
