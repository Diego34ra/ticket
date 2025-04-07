package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioPatchDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.message.MessageResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.CustomAccessDeniedException;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<MessageResponseDTO> criar(UsuarioRequestDTO usuarioCreate) {
        Usuario usuario = mapper.mapTo(usuarioCreate, Usuario.class);
        usuario.setContatos(mapper.toList(usuarioCreate.getContatos(), Telefone.class));
        usuario.setTipoUsuario(UsuarioRole.CLIENTE);
        usuario.getContatos().forEach(telefone -> telefone.setUsuario(usuario));

        boolean emailExists = usuarioRepository.existsByEmail(usuarioCreate.getEmail().toLowerCase());
        boolean cpfExists = usuarioRepository.existsByCpf(usuarioCreate.getCpf().trim());

        if(emailExists || cpfExists) {
            String errorMessage = "Usuário já cadastrado. Campo duplicado: ";
            errorMessage += emailExists ? "Email" : "CPF";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponseDTO
                    .builder()
                    .code(400)
                    .status("Bad Request")
                    .message(errorMessage)
                    .build());
        }

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
    public List<UsuarioResponseDTO> buscarTodos() {
        return mapper.toList(usuarioRepository.findAll(), UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO buscaPorId(Long uuid) {
        Long uuidAuth = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        boolean somenteCliente = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .allMatch(role -> role.equals("ROLE_CLIENTE"));

        if(somenteCliente && !Objects.equals(uuidAuth, uuid))
            throw new CustomAccessDeniedException("Acesso negado.Você não tem permissão para acessar este recurso.");

        Usuario usuario = usuarioRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum usuário com esse id."));
        return mapper.mapTo(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO atualizar(Long uuid, UsuarioRequestDTO usuarioUpdate) {
        Usuario usuario = mapper.mapTo(usuarioUpdate, Usuario.class);
        BeanUtils.copyProperties(usuarioUpdate, usuario, objectUtils.getNullPropertyNames(usuarioUpdate));
        return mapper.mapTo(usuarioRepository.save(usuario), UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO atualizarPapel(Long uuid, UsuarioPatchDTO usuarioPatchDTO) {
        Usuario usuario = usuarioRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Usuário nao encontrado."));

        usuario.setTipoUsuario(usuarioPatchDTO.getTipoUsuario());
        return mapper.mapTo(usuarioRepository.save(usuario), UsuarioResponseDTO.class);
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
