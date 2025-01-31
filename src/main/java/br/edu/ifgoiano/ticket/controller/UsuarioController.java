package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.MessageResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@Tag(name = "Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Criar um Usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    private ResponseEntity<MessageResponseDTO> criar(@RequestBody @Valid UsuarioInputDTO usuario){
        return usuarioService.criar(usuario);
    }

    @GetMapping
    @Operation(summary = "Buscar todas os Usuarios")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuarios buscados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioOutputDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    private ResponseEntity<List<UsuarioOutputDTO>> buscarTodos(){
        var usuarioList = usuarioService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(usuarioList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar um Usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario buscado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    private ResponseEntity<UsuarioOutputDTO> buscarPorId(@PathVariable Long id){
        var usuario = usuarioService.buscaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioOutputDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    private ResponseEntity<UsuarioOutputDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioInputDTO usuarioInputDTO){
        var usuario = usuarioService.atualizar(id,usuarioInputDTO);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar um Usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    private ResponseEntity<?> deletarPorId(@PathVariable Long id){
        usuarioService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
