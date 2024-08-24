package br.edu.ifgoiano.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsuarioRole {
    CLIENTE,
    FUNCIONARIO,
    GERENTE,
    ADMINISTRADOR;

    public static UsuarioRole getPadrao(){
        return CLIENTE;
    }
}
