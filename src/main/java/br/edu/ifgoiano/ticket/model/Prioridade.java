package br.edu.ifgoiano.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Prioridade {
    BAIXO,
    NORMAL,
    ALTA,
    MUITO_ALTA
}
