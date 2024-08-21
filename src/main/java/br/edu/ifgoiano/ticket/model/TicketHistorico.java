package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_ticketHistorico")
public class TicketHistorico {
    @Id
    @Column(name = "ticketHistorico_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campo;
    private String ultimoValor;
    private String novoValor;
    private LocalDateTime dataAlteracao;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private Usuario agente;
}
