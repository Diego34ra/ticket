package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_telefone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

    @Id
    @Column(name = "telefone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ddi;
    private String ddd;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
