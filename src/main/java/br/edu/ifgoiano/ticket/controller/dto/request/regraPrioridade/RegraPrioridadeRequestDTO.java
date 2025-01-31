package br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade;

import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegraPrioridadeRequestDTO {
    private Categoria categoria;
    private Departamento departamento;
    private Prioridade prioridade;
}
