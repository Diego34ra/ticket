package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.autenticacao.AuthenticationRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.login.LoginResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/login")
@Tag(name = "Autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @Operation(summary = "Realizar login")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario logado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Acesso negado.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    private ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getLogin(), authenticationRequestDTO.getPassword());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token  = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }
}
