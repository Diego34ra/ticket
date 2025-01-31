package br.edu.ifgoiano.ticket.controller.dto.response.regraPrioridade;

import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoResponseDTO;
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
public class RegraPrioridadeResponseDTO {
    private Long id;

    private Categoria categoria;

    private DepartamentoResponseDTO departamento;

    private Prioridade prioridade;
}
