package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
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
@Entity(name = "tb_registroTrabalho")
public class RegistroTrabalho {

    @Id
    @Column(name = "registroTrabalho_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Duration duration;
    private LocalDateTime horarioFim;
    private LocalDateTime horarioInicio;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private Usuario agente;

    @PrePersist
    @PreUpdate
    private void calculateDuration() {
        if (horarioFim != null && horarioInicio != null) {
            duration = Duration.between(horarioInicio, horarioFim);
        }
    }
}
