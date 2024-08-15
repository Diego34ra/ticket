package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_regraPrioridade")
public class RegraPrioridade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Departamento departamento;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
}
