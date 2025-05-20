package br.edu.ifgoiano.ticket.controller.dto.response.comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String contentType;
    private String size;
    private String downloadUrl;
}
