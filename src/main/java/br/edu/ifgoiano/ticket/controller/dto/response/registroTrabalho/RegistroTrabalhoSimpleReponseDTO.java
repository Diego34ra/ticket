package br.edu.ifgoiano.ticket.controller.dto.response.registroTrabalho;

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
public class RegistroTrabalhoSimpleReponseDTO {
    private Long id;
    private String descricao;
    private Duration duracao;
    private LocalDateTime horarioFim;
    private LocalDateTime horarioInicio;
    private UsuarioSimpleResponseDTO agente;
}
