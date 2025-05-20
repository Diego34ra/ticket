package br.edu.ifgoiano.ticket.controller.dto.response.comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDownload {
    private Resource resource;
    private String contentType;
}
