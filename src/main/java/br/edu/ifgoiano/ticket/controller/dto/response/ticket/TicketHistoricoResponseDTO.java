package br.edu.ifgoiano.ticket.controller.dto.response.ticket;

import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioSimpleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistoricoResponseDTO {
    private Long id;
    private String campo;
    private String ultimoValor;
    private String novoValor;
    private LocalDateTime dataAlteracao;
    private UsuarioSimpleResponseDTO agente;
}
