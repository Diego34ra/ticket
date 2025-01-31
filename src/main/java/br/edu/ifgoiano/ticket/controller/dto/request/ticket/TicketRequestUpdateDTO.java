package br.edu.ifgoiano.ticket.controller.dto.request.ticket;

import br.edu.ifgoiano.ticket.model.*;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestUpdateDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private LocalDateTime dataMaximaResolucao;
    private StatusTicket status;
    private Prioridade prioridade;
    private Categoria categoria;
    private Departamento departamento;
    private Usuario cliente;
    private Usuario responsavel;
}
