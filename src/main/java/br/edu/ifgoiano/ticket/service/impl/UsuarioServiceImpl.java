package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.UsuarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.model.Telefone;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.model.UsuarioRole;
import br.edu.ifgoiano.ticket.repository.UsuarioRepository;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MyModelMapper mapper;

    @Override
    public UsuarioOutputDTO criar(UsuarioInputDTO usuarioCreate) {
        Usuario usuario = mapper.mapTo(usuarioCreate, Usuario.class);
        usuario.setContatos(mapper.toList(usuarioCreate.getContatos(), Telefone.class));
        usuario.setTipoUsuario(UsuarioRole.getPadrao());
        usuario.getContatos().forEach(telefone -> telefone.setUsuario(usuario));
        return mapper.mapTo(usuarioRepository.save(usuario), UsuarioOutputDTO.class);
    }

    @Override
    public List<UsuarioOutputDTO> buscarTodos() {
        return mapper.toList(usuarioRepository.findAll(),UsuarioOutputDTO.class);
    }

    @Override
    public UsuarioOutputDTO buscaPorId(String uuid) {
        return mapper.mapTo(usuarioRepository.findById(UUID.fromString(uuid)),UsuarioOutputDTO.class);
    }
}
