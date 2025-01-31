package br.edu.ifgoiano.ticket.controller.dto.response.departamento;

import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioSimpleOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoOutputDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String email;
    private String horarioFuncionamento;
    private UsuarioSimpleOutputDTO gerente;
}
