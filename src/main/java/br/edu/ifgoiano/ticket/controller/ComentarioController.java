package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.service.ComentarioService;
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
@RequestMapping("api/v1/comentarios")
@Tag(name = "Comentario")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "Criar um comentário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ComentarioOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<ComentarioOutputDTO> criar(@RequestParam Long ticketId,
                                                     @RequestParam Long usuarioId,
                                                     @ModelAttribute ComentarioInputDTO conteudo) {
        var comentarioCriado = comentarioService.criar(ticketId,usuarioId,conteudo);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os comentários de um ticket")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentários buscados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ComentarioOutputDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<List<ComentarioOutputDTO>> buscarPorTicketId(@RequestParam Long ticketId){
        var comentarioList = comentarioService.buscarPorTicketId(ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(comentarioList);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um comentário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ComentarioOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<ComentarioOutputDTO> atualizar(@RequestBody Long id,ComentarioInputUpdateDTO comentarioInputUpdateDTO){
        var comentarioAtualizado = comentarioService.atualizar(id,comentarioInputUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(comentarioAtualizado);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar um comentário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        comentarioService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{id}/anexo")
    @Operation(summary = "Deletar um anexo de um comentário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Anexo deletado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<?> deletarAnexoPorNome(@PathVariable Long id,@RequestParam String filename){
        comentarioService.deletarAnexoPorNome(id,filename);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
