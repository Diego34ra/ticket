package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_comentario")
public class Comentario {
    @Id
    @Column(name = "comentario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conteudo;
    @OneToOne
    private Usuario autor;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "comentario", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Anexo> anexos;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
