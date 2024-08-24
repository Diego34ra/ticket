package br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho;

import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioSimpleOutputDTO;
import jakarta.persistence.ManyToOne;
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
public class RegistroTrabalhoOutputDTO {
    private Long id;
    private String descricao;
    private Duration duration;
    private LocalDateTime horarioFim;
    private LocalDateTime horarioInicio;
    private TicketSimpleOutputDTO ticket;
    private UsuarioSimpleOutputDTO agente;
}
