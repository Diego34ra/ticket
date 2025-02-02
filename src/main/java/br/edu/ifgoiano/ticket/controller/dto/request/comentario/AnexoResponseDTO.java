package br.edu.ifgoiano.ticket.controller.dto.request.comentario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnexoResponseDTO {
    private Long id;
    private String nomeArquivo;
    private String tipoArquivo;
    private String tamanho;
    private String downloadUrl;
}
