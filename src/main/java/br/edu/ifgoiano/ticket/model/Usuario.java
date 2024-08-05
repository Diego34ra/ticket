package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;

    private String email;

    private String cpf;

    private String senha;

    @Enumerated(EnumType.STRING)
    private UsuarioRole tipoUsuario;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    private List<Telefone> contatos;


}
