package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.AnexoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.*;
import br.edu.ifgoiano.ticket.repository.AnexoRepository;
import br.edu.ifgoiano.ticket.repository.ComentarioRepository;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.service.ComentarioService;
import br.edu.ifgoiano.ticket.service.TicketService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private TicketRespository ticketRespository;

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MyModelMapper mapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String getAbsolutePath() {
        String userDir = System.getProperty("user.dir");
        Path srcPath = Paths.get(userDir);
        return srcPath.toAbsolutePath().toString();
    }

    @Override
    public ComentarioOutputDTO criar(Long ticketId, Long usuarioId, ComentarioInputDTO comentarioInputDTO) {
        Comentario comentarioCriar = mapper.mapTo(comentarioInputDTO,Comentario.class);
        Ticket ticket = mapper.mapTo(ticketService.buscarPorId(ticketId),Ticket.class);
        Usuario autor = mapper.mapTo(usuarioService.buscaPorId(usuarioId), Usuario.class);
        ticket.getComentarios().add(comentarioCriar);
        comentarioCriar.setAutor(autor);
        comentarioCriar.setTicket(ticket);


        Comentario comentario = comentarioRepository.save(comentarioCriar);
        ticketRespository.save(ticket);

        List<Anexo> anexos = new ArrayList<>();

        Arrays.stream(comentarioInputDTO.getAnexos()).forEach(anexo -> anexos.add(salvarAnexo(comentario.getId(),anexo)));

        anexos.forEach(anexo -> anexo.setComentario(comentario));

        ComentarioOutputDTO comentarioOutputDTO = mapper.mapTo(comentario,ComentarioOutputDTO.class);
        comentarioOutputDTO.setAnexos(mapper.toList(anexoRepository.saveAll(anexos), AnexoOutputDTO.class));

        return comentarioOutputDTO;
    }

    @Override
    public ComentarioOutputDTO buscarPorId(Long id) {
        return mapper.mapTo(comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum comentário com esse id.")),ComentarioOutputDTO.class);
    }

    @Override
    public List<ComentarioOutputDTO> buscarPorTicketId(Long ticketId) {
        return mapper.toList(comentarioRepository.findByTicketId(ticketId), ComentarioOutputDTO.class);
    }

    @Override
    public ComentarioOutputDTO atualizar(Long comentarioId, ComentarioInputUpdateDTO comentarioInputUpdateDTO) {
        return null;
    }

    @Override
    public void deletarPorId(Long comentarioId) {
        ComentarioOutputDTO comentarioOutputDTO = buscarPorId(comentarioId);
        comentarioOutputDTO.getAnexos().forEach(anexo -> deletarAnexo(comentarioId,anexo.getNomeArquivo()));
        comentarioRepository.deleteById(comentarioId);
    }

    @Override
    public void deletarAnexoPorNome(Long comentarioId, String fileName) {
        deletarAnexo(comentarioId,fileName);
    }

    public Anexo salvarAnexo(Long comentarioId, MultipartFile file) {
        try {
            Path uploadPath = Paths.get(getAbsolutePath() + uploadDir, comentarioId.toString());
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String originalFilename = file.getOriginalFilename();
            String contentType = Files.probeContentType(filePath);
            long sizeInBytes = Files.size(filePath);
            String size = formatSize(sizeInBytes);
            String downloadUri = "/api/v1/comments/" + comentarioId + "/download-anexo/"+originalFilename;

            return new Anexo(originalFilename,contentType,size,downloadUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletarAnexo(Long comentarioId, String filename) {
        try {
            Path uploadPath = Paths.get(getAbsolutePath() + uploadDir, comentarioId.toString());

            Path filePath = uploadPath.resolve(filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                throw new RuntimeException("Arquivo não encontrado: " + filename);
            }

            if (Files.isDirectory(uploadPath) && Files.list(uploadPath).findAny().isEmpty()) {
                Files.delete(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar arquivo: " + filename, e);
        }
    }

    public static String formatSize(long sizeInBytes) {
        double size = sizeInBytes;
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        return String.format("%.2f %s", size, units[unitIndex]);
    }
}
