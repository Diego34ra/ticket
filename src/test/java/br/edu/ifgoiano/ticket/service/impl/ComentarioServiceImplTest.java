package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.AnexoResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.ComentarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioResponseDTO;
import br.edu.ifgoiano.ticket.model.Anexo;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.ComentarioRepository;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.service.TicketService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComentarioServiceImplTest {

    @InjectMocks
    private ComentarioServiceImpl comentarioService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private TicketRespository ticketRespository;

    @Mock
    private AnexoServiceImpl anexoService;

    @Mock
    private TicketService ticketService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private MyModelMapper mapper;

    @Mock
    private ObjectUtils objectUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Test
    void criar() {
        Long ticketId = 1L;
        Long usuarioId = 1L;

        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setComentarios(new ArrayList<>());

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setId(ticketId.toString());

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuarioId);

        ComentarioRequestDTO comentarioInputDTO = new ComentarioRequestDTO();
        comentarioInputDTO.setConteudo("Texto do comentário");
        comentarioInputDTO.setAnexos(new MultipartFile[]{});

        Comentario comentario = new Comentario();
        comentario.setId(1L);
        comentario.setConteudo("Texto do comentário");

        ComentarioResponseDTO comentarioResponseDTO = new ComentarioResponseDTO();
        comentarioResponseDTO.setConteudo("Texto do comentário");

        List<Anexo> anexoList = List.of(new Anexo("arquivo.pdf", "application/pdf", "10 KB", "/download/arquivo.pdf"));
        List<AnexoResponseDTO> anexoResponseDTOList = List.of(new AnexoResponseDTO(1L,"arquivo.pdf", "application/pdf", "10 KB", "/download/arquivo.pdf"));

        when(ticketService.buscarPorId(ticketId)).thenReturn(ticketResponseDTO);
        when(mapper.mapTo(ticketResponseDTO,Ticket.class)).thenReturn(ticket);
        when(usuarioService.buscaPorId(usuarioId)).thenReturn(usuarioResponseDTO);
        when(mapper.mapTo(usuarioResponseDTO,Usuario.class)).thenReturn(usuario);
        when(mapper.mapTo(comentarioInputDTO, Comentario.class)).thenReturn(comentario);
        when(comentarioRepository.save(comentario)).thenReturn(comentario);
        when(mapper.mapTo(comentario, ComentarioResponseDTO.class)).thenReturn(comentarioResponseDTO);
        when(anexoService.salvarAnexos(comentario, comentarioInputDTO.getAnexos())).thenReturn(anexoList);
        when(mapper.toList(anexoList, AnexoResponseDTO.class)).thenReturn(anexoResponseDTOList);

        ComentarioResponseDTO resultado = comentarioService.criar(ticketId, usuarioId, comentarioInputDTO);

        assertNotNull(resultado);
        assertEquals("Texto do comentário", resultado.getConteudo());
        verify(comentarioRepository).save(comentario);
        verify(ticketRespository).save(ticket);
    }

    @Test
    void buscarPorId() {
        Long comentarioId = 1L;
        Comentario comentario = new Comentario();
        comentario.setId(comentarioId);
        comentario.setConteudo("Texto do comentário");

        when(comentarioRepository.findById(comentarioId)).thenReturn(Optional.of(comentario));

        Optional<Comentario> resultado = comentarioService.buscarPorId(comentarioId);

        assertTrue(resultado.isPresent());
        assertEquals(comentarioId, resultado.get().getId());
    }

    @Test
    void buscarPorTicketId() {
        Long ticketId = 1L;

        Comentario comentario = new Comentario();
        comentario.setId(1L);
        comentario.setConteudo("Texto do comentário");

        ComentarioResponseDTO comentarioResponseDTO = new ComentarioResponseDTO();
        comentarioResponseDTO.setId(1L);
        comentarioResponseDTO.setConteudo("Texto do comentário");

        when(comentarioRepository.findByTicketId(ticketId)).thenReturn(List.of(comentario));
        when(mapper.toList(List.of(comentario), ComentarioResponseDTO.class)).thenReturn(List.of(comentarioResponseDTO));

        List<ComentarioResponseDTO> resultado = comentarioService.buscarPorTicketId(ticketId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(comentarioRepository).findByTicketId(ticketId);
    }

    @Test
    void atualizar() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        String userId = "1";
        when(authentication.getPrincipal()).thenReturn(userId);
        Long comentarioId = 1L;
        Comentario comentarioExistente = new Comentario();
        comentarioExistente.setId(comentarioId);
        comentarioExistente.setConteudo("Texto antigo");

        ComentarioRequestUpdateDTO updateDTO = new ComentarioRequestUpdateDTO();
        updateDTO.setConteudo("Texto atualizado");

        Comentario comentarioAtualizado = new Comentario();
        comentarioExistente.setId(comentarioId);
        comentarioExistente.setConteudo("Texto atualizado");

        ComentarioResponseDTO comentarioResponseDTO = new ComentarioResponseDTO();
        comentarioResponseDTO.setId(comentarioId);
        comentarioResponseDTO.setConteudo("Texto atualizado");

        when(comentarioRepository.findByAutorIdAndId(comentarioId,Long.parseLong(userId))).thenReturn(Optional.of(comentarioExistente));
        when(objectUtils.getNullPropertyNames(updateDTO)).thenReturn(new String[]{});
        when(comentarioRepository.save(comentarioExistente)).thenReturn(comentarioAtualizado);
        when(mapper.mapTo(comentarioAtualizado, ComentarioResponseDTO.class)).thenReturn(comentarioResponseDTO);

        ComentarioResponseDTO resultado = comentarioService.atualizar(comentarioId, updateDTO);

        assertNotNull(resultado);
        assertEquals("Texto atualizado", resultado.getConteudo());
        verify(comentarioRepository).save(comentarioExistente);
    }

    @Test
    void deletarPorId() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        String userId = "1";
        when(authentication.getPrincipal()).thenReturn(userId);
        Long comentarioId = 1L;
        Comentario comentario = new Comentario();
        comentario.setId(comentarioId);

        when(comentarioRepository.findByAutorIdAndId(comentarioId,Long.parseLong(userId))).thenReturn(Optional.of(comentario));
        doNothing().when(anexoService).deletarAnexos(comentario);

        comentarioService.deletarPorId(comentarioId);

        verify(anexoService).deletarAnexos(comentario);
        verify(comentarioRepository).deleteById(comentarioId);
    }

}