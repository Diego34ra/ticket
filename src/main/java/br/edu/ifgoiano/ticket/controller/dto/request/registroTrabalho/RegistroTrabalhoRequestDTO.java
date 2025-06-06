package br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroTrabalhoRequestDTO {
    private String descricao;
    private LocalDateTime horarioFim;
    private LocalDateTime horarioInicio;
}
