package br.edu.ifgoiano.ticket.model;

import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

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

    private static final Map<Prioridade, Integer> HORAS_RESOLUCAO = Map.of(
            Prioridade.BAIXO, 120,
            Prioridade.NORMAL, 72,
            Prioridade.ALTA, 24,
            Prioridade.MUITO_ALTA, 5
    );

    public int getHorasResolucao() {
        Integer horas = HORAS_RESOLUCAO.get(prioridade);
        if (horas == null) {
            throw new IllegalArgumentException("Prioridade inválida: " + prioridade);
        }
        return horas;
    }
}
