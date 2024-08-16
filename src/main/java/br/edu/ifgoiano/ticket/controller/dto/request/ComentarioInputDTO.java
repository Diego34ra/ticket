package br.edu.ifgoiano.ticket.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioInputDTO {
    private String conteudo;

//    private MultipartFile anexo;

}
