package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.autenticacao.RefreshTokenRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.login.LoginResponseDTO;
import br.edu.ifgoiano.ticket.model.Usuario;

public interface TokenService {

    LoginResponseDTO realizarLogin(Usuario usuario);

    LoginResponseDTO realizarRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

    String getUsernameFromToken(String token);

    String getRolesFromToken(String token);
}
