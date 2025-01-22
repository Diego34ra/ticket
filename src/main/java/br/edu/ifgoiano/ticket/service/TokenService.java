package br.edu.ifgoiano.ticket.service;

public interface TokenService {

    String validateToken(String token);

    String getUsernameFromToken(String token);

    String getRolesFromToken(String token);
}
