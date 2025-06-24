package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.AnexoResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.ComentarioResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.FileResponseDownload;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.*;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.repository.AnexoRepository;
import br.edu.ifgoiano.ticket.repository.ComentarioRepository;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.service.ComentarioService;
import br.edu.ifgoiano.ticket.service.TicketService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private TicketRespository ticketRespository;

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private AnexoServiceImpl anexoService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String getAbsolutePath() {
        String userDir = System.getProperty("user.dir");
        Path srcPath = Paths.get(userDir);
        return srcPath.toAbsolutePath().toString();
    }

    @Override
    public ComentarioResponseDTO criar(Long ticketId, ComentarioRequestDTO comentarioInputDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uuidAuth = Long.valueOf((String) authentication.getPrincipal());
        Ticket ticket = mapper.mapTo(ticketService.buscarPorId(ticketId),Ticket.class);
        Usuario autor = mapper.mapTo(usuarioService.buscaPorId(uuidAuth), Usuario.class);
        Comentario comentarioCriar = mapper.mapTo(comentarioInputDTO,Comentario.class);
        ticket.getComentarios().add(comentarioCriar);
        comentarioCriar.setAutor(autor);
        comentarioCriar.setTicket(ticket);
        Comentario comentario = comentarioRepository.save(comentarioCriar);
        ticketRespository.save(ticket);

        ComentarioResponseDTO comentarioResponseDTO = mapper.mapTo(comentario, ComentarioResponseDTO.class);

        List<Anexo> anexoList = anexoService.salvarAnexos(comentario,comentarioInputDTO.getAnexos());
        if(anexoList != null && !anexoList.isEmpty())
            comentarioResponseDTO.setAnexos(mapper.toList(anexoList, AnexoResponseDTO.class));


        return comentarioResponseDTO;
    }

    @Override
    public Optional<Comentario> buscarPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    @Override
    public FileResponseDownload downloadAnexo(Long comentarioId, String filename) {
        try {
            Path file = Paths.get(getAbsolutePath()+uploadDir).resolve(comentarioId.toString()).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return new FileResponseDownload(resource,contentType);
            } else {
                throw new RuntimeException("File not found or not readable: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while downloading file: " + e.getMessage());
        }
    }

    @Override
    public List<ComentarioResponseDTO> buscarPorTicketId(Long ticketId) {
        return mapper.toList(comentarioRepository.findByTicketId(ticketId), ComentarioResponseDTO.class);
    }

    @Override
    public ComentarioResponseDTO atualizar(Long comentarioId, ComentarioRequestUpdateDTO comentarioInputUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uuidAuth = Long.valueOf((String) authentication.getPrincipal());
        Comentario comentario = comentarioRepository.findByAutorIdAndId(uuidAuth,comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada nenhum comentario com esse id."));
        BeanUtils.copyProperties(comentarioInputUpdateDTO,comentario,objectUtils.getNullPropertyNames(comentarioInputUpdateDTO));
        return mapper.mapTo(comentarioRepository.save(comentario), ComentarioResponseDTO.class);
    }

    @Override
    public void deletarPorId(Long comentarioId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uuidAuth = Long.valueOf((String) authentication.getPrincipal());

        Optional<Comentario> comentarioOptional = comentarioRepository.findByAutorIdAndId(uuidAuth,comentarioId);
        if(comentarioOptional.isPresent()) {
            anexoService.deletarAnexos(comentarioOptional.get());
            comentarioRepository.deleteById(comentarioId);
        }
    }

    @Override
    public void deletarAnexoPorNome(Long comentarioId, String fileName) {
        anexoService.deletarAnexo(comentarioId,fileName);
    }
}
