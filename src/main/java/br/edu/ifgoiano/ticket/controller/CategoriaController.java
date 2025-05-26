package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.categoria.CategoriaDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.service.CategoriaService;
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
@RequestMapping("api/v1/categorias")
@Tag(name = "Categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @Operation(summary = "Criar uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "categoriaCache", allEntries = true)
    public ResponseEntity<Categoria> criar(@RequestBody CategoriaDTO categoriaDTO){
        var categoriaCriada = categoriaService.criar(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @GetMapping
    @Operation(summary = "Buscar todas as categorias")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categorias buscadas com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Categoria.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @Cacheable(value = "categoriaCache")
    public ResponseEntity<List<Categoria>> buscarTodos(){
        var categoriaList = categoriaService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(categoriaList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria buscada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @Cacheable(value = "categoriaCache")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id){
        var categoria = categoriaService.buscaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "categoriaCache", allEntries = true)
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO){
        var categoriaAtualizada = categoriaService.atualizar(id,categoriaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaAtualizada);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso."),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso. ",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "categoriaCache", allEntries = true)
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        categoriaService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
