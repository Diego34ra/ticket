package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.AnexoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.model.Anexo;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.ComentarioRepository;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.service.TicketService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void criar() {
        Long ticketId = 1L;
        Long usuarioId = 1L;

        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setComentarios(new ArrayList<>());

        TicketOutputDTO ticketOutputDTO = new TicketOutputDTO();
        ticketOutputDTO.setId(ticketId.toString());

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        UsuarioOutputDTO usuarioOutputDTO = new UsuarioOutputDTO();
        usuarioOutputDTO.setId(usuarioId);

        ComentarioRequestDTO comentarioInputDTO = new ComentarioRequestDTO();
        comentarioInputDTO.setConteudo("Texto do comentário");
        comentarioInputDTO.setAnexos(new MultipartFile[]{});

        Comentario comentario = new Comentario();
        comentario.setId(1L);
        comentario.setConteudo("Texto do comentário");

        ComentarioOutputDTO comentarioOutputDTO = new ComentarioOutputDTO();
        comentarioOutputDTO.setConteudo("Texto do comentário");

        List<Anexo> anexoList = List.of(new Anexo("arquivo.pdf", "application/pdf", "10 KB", "/download/arquivo.pdf"));
        List<AnexoOutputDTO> anexoOutputDTOList = List.of(new AnexoOutputDTO(1L,"arquivo.pdf", "application/pdf", "10 KB", "/download/arquivo.pdf"));

        when(ticketService.buscarPorId(ticketId)).thenReturn(ticketOutputDTO);
        when(mapper.mapTo(ticketOutputDTO,Ticket.class)).thenReturn(ticket);
        when(usuarioService.buscaPorId(usuarioId)).thenReturn(usuarioOutputDTO);
        when(mapper.mapTo(usuarioOutputDTO,Usuario.class)).thenReturn(usuario);
        when(mapper.mapTo(comentarioInputDTO, Comentario.class)).thenReturn(comentario);
        when(comentarioRepository.save(comentario)).thenReturn(comentario);
        when(mapper.mapTo(comentario, ComentarioOutputDTO.class)).thenReturn(comentarioOutputDTO);
        when(anexoService.salvarAnexos(comentario, comentarioInputDTO.getAnexos())).thenReturn(anexoList);
        when(mapper.toList(anexoList, AnexoOutputDTO.class)).thenReturn(anexoOutputDTOList);

        ComentarioOutputDTO resultado = comentarioService.criar(ticketId, usuarioId, comentarioInputDTO);

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

        ComentarioOutputDTO comentarioOutputDTO = new ComentarioOutputDTO();
        comentarioOutputDTO.setId(1L);
        comentarioOutputDTO.setConteudo("Texto do comentário");

        when(comentarioRepository.findByTicketId(ticketId)).thenReturn(List.of(comentario));
        when(mapper.toList(List.of(comentario),ComentarioOutputDTO.class)).thenReturn(List.of(comentarioOutputDTO));

        List<ComentarioOutputDTO> resultado = comentarioService.buscarPorTicketId(ticketId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(comentarioRepository).findByTicketId(ticketId);
    }

    @Test
    void atualizar() {
        Long comentarioId = 1L;
        Comentario comentarioExistente = new Comentario();
        comentarioExistente.setId(comentarioId);
        comentarioExistente.setConteudo("Texto antigo");

        ComentarioRequestUpdateDTO updateDTO = new ComentarioRequestUpdateDTO();
        updateDTO.setConteudo("Texto atualizado");

        Comentario comentarioAtualizado = new Comentario();
        comentarioExistente.setId(comentarioId);
        comentarioExistente.setConteudo("Texto atualizado");

        ComentarioOutputDTO comentarioOutputDTO = new ComentarioOutputDTO();
        comentarioOutputDTO.setId(comentarioId);
        comentarioOutputDTO.setConteudo("Texto atualizado");

        when(comentarioRepository.findById(comentarioId)).thenReturn(Optional.of(comentarioExistente));
        when(objectUtils.getNullPropertyNames(updateDTO)).thenReturn(new String[]{});
        when(comentarioRepository.save(comentarioExistente)).thenReturn(comentarioAtualizado);
        when(mapper.mapTo(comentarioAtualizado,ComentarioOutputDTO.class)).thenReturn(comentarioOutputDTO);

        ComentarioOutputDTO resultado = comentarioService.atualizar(comentarioId, updateDTO);

        assertNotNull(resultado);
        assertEquals("Texto atualizado", resultado.getConteudo());
        verify(comentarioRepository).save(comentarioExistente);
    }

    @Test
    void deletarPorId() {
        Long comentarioId = 1L;
        Comentario comentario = new Comentario();
        comentario.setId(comentarioId);

        when(comentarioRepository.findById(comentarioId)).thenReturn(Optional.of(comentario));

        comentarioService.deletarPorId(comentarioId);

        verify(anexoService).deletarAnexos(comentario);
        verify(comentarioRepository).deleteById(comentarioId);
    }

}