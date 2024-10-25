package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Telefone;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.model.enums.UsuarioRole;
import br.edu.ifgoiano.ticket.repository.UsuarioRepository;
import br.edu.ifgoiano.ticket.service.EmailService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectUtils objectUtils;

    @Autowired
    private MyModelMapper mapper;

    @Override
    public UsuarioOutputDTO criar(UsuarioInputDTO usuarioCreate) {
        Usuario usuario = mapper.mapTo(usuarioCreate, Usuario.class);
        usuario.setContatos(mapper.toList(usuarioCreate.getContatos(), Telefone.class));
        usuario.setTipoUsuario(UsuarioRole.GERENTE);
        usuario.getContatos().forEach(telefone -> telefone.setUsuario(usuario));

        var usuarioSalvo = usuarioRepository.save(usuario);

        emailService.enviarUsuarioCadastradoEmail(usuarioSalvo);
        return mapper.mapTo(usuarioSalvo, UsuarioOutputDTO.class);
    }

    @Override
    public List<UsuarioOutputDTO> buscarTodos() {
        return mapper.toList(usuarioRepository.findAll(),UsuarioOutputDTO.class);
    }

    @Override
    public UsuarioOutputDTO buscaPorId(Long uuid) {
        Usuario usuario = usuarioRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum usuário com esse id."));
        return mapper.mapTo(usuario,UsuarioOutputDTO.class);
    }

    @Override
    public UsuarioOutputDTO atualizar(Long uuid, UsuarioInputDTO usuarioUpdate) {
        Usuario usuario = mapper.mapTo(usuarioUpdate, Usuario.class);
        BeanUtils.copyProperties(usuarioUpdate, usuario, objectUtils.getNullPropertyNames(usuarioUpdate));
        return mapper.mapTo(usuarioRepository.save(usuario), UsuarioOutputDTO.class);
    }

    @Override
    public void deletePorId(Long uuid) {
        usuarioRepository.deleteById(uuid);
    }

    @Override
    public boolean verificarSeUsuarioEhGerente(Long id) {
        return !usuarioRepository.isUsuarioGerente(id);
    }
}
