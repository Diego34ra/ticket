package br.edu.ifgoiano.ticket.controller.dto.request.departamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoSimpleOutputDTO {
    private Long id;
    private String nome;
    private String descricao;
}
