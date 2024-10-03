package br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade;

import br.edu.ifgoiano.ticket.controller.dto.request.departamento.DepartamentoOutputDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
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
