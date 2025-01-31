package br.edu.ifgoiano.ticket.controller.dto.response.registroTrabalho;

import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketSimpleResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioSimpleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroTrabalhoReponseDTO {
    private Long id;
    private String descricao;
    private Duration duration;
    private LocalDateTime horarioFim;
    private LocalDateTime horarioInicio;
    private TicketSimpleResponseDTO ticket;
    private UsuarioSimpleResponseDTO agente;
}
