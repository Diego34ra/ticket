package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.repository.ComentarioRepository;
import br.edu.ifgoiano.ticket.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private MyModelMapper mapper;

    @Override
    public Comentario criar(Long ticketId, Long usuarioId, ComentarioInputDTO comentarioInputDTO) {
        Comentario comentario = mapper.mapTo(comentarioInputDTO,Comentario.class);
        System.out.println(comentario.getConteudo());
        return comentarioRepository.save(comentario);
    }
}
