package br.edu.ifgoiano.ticket.controller.dto.request;

import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegraPrioridadeOutputDTO {
    private Long id;

    private Categoria categoria;

    private DepartamentoOutputDTO departamento;

    private Prioridade prioridade;
}
