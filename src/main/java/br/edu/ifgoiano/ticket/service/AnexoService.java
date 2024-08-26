package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.model.Anexo;
import br.edu.ifgoiano.ticket.model.Comentario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnexoService {

    List<Anexo> salvarAnexos (Comentario comentario, MultipartFile[] files);

    void deletarAnexos (Comentario comentario);
}
