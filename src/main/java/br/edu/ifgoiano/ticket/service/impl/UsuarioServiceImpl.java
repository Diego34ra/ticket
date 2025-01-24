package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.MessageResponseDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectUtils objectUtils;

    @Autowired
    private MyModelMapper mapper;

    @Override
    public ResponseEntity<MessageResponseDTO> criar(UsuarioInputDTO usuarioCreate) {
        Usuario usuario = mapper.mapTo(usuarioCreate, Usuario.class);
        usuario.setContatos(mapper.toList(usuarioCreate.getContatos(), Telefone.class));
        usuario.setTipoUsuario(UsuarioRole.GERENTE);
        usuario.getContatos().forEach(telefone -> telefone.setUsuario(usuario));

        if(this.usuarioRepository.findByEmail(usuarioCreate.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponseDTO
                    .builder()
                    .code(400)
                    .status("Bad Request")
                    .message("Usuário já cadastrado.")
                    .build());

        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(encryptedPassword);

        var usuarioSalvo = usuarioRepository.save(usuario);

        emailService.enviarUsuarioCadastradoEmail(usuarioSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponseDTO
                .builder()
                .code(201)
                .status("Created")
                .message("Usuário criado com sucesso.")
                .build());
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }
}
