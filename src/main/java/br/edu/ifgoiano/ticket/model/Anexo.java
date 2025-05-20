package br.edu.ifgoiano.ticket.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_anexo")
@ToString(exclude = {"comentario"})
public class Anexo {

    @Id
    @Column(name = "anexo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeArquivo;
    private String tipoArquivo;
    private String tamanho;
    private String downloadUrl;
    @ManyToOne
    private Comentario comentario;

    public Anexo(String nomeArquivo, String tipoArquivo, String tamanho, String downloadUrl) {
        this.nomeArquivo = nomeArquivo;
        this.tipoArquivo = tipoArquivo;
        this.tamanho = tamanho;
        this.downloadUrl = downloadUrl;
    }
}
