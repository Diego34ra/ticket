package br.edu.ifgoiano.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String nomeArquivo;
    private String tipoArquivo;
    private String tamanho;
    private String downloadUrl;
}
