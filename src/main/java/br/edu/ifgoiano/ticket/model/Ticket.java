package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_ticket")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private LocalDateTime dataMaximaResolucao;
    @Enumerated(EnumType.STRING)
    private StatusTicket status;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Usuario cliente;
    @ManyToOne
    private Usuario responsavel;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "ticket", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comentario> comentarios;
}
