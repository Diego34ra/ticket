package br.edu.ifgoiano.ticket.controller.dto.request;

import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistoricoOutputDTO {
    private Long id;
    private String campo;
    private String ultimoValor;
    private String novoValor;
    private LocalDateTime dataAlteracao;
    private UsuarioOutputDTO agente;
}
