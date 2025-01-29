package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.model.Usuario;

public interface TokenService {

    String gerarToken(Usuario usuario);

    String validateToken(String token);

    String getUsernameFromToken(String token);

    String getRolesFromToken(String token);
}
