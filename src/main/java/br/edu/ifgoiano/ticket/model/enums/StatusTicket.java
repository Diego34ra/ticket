package br.edu.ifgoiano.ticket.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusTicket {
    ABERTO,
    EM_ANDAMENTO,
    EM_ESPERA,
    FINALIZADO
}
