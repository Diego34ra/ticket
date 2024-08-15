package br.edu.ifgoiano.ticket.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepartamentoDTO {
    private String nome;
    private String descricao;
    private String email;
    private String horarioFuncionamento;
}
