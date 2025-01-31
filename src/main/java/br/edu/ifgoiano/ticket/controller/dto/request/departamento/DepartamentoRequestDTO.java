package br.edu.ifgoiano.ticket.controller.dto.request.departamento;

import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioSimpleRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoRequestDTO {
    private String nome;
    private String descricao;
    private String email;
    private String horarioFuncionamento;
    private UsuarioSimpleRequestDTO gerente;
}
