package br.edu.ifgoiano.ticket.controller.dto.request.ticket;

import br.edu.ifgoiano.ticket.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private Departamento departamento;
    private Usuario cliente;
    private Usuario responsavel;
}
