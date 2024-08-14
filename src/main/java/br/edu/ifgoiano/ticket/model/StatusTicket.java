package br.edu.ifgoiano.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusTicket {
    ABERTO,
    EM_ANDAMENTO,
    FINALIZADO
}
