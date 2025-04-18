package br.edu.ifgoiano.ticket.controller.dto.request.comentario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioRequestDTO {
    private String conteudo;

    private MultipartFile[] anexos;

}
