package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.comentario.ComentarioOutputDTO;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.model.FileResponse;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
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

        List<FileResponse> anexos = new ArrayList<>();

        Arrays.stream(comentarioInputDTO.getAnexos()).forEach(anexo -> anexos.add(salvarAnexo(comentario.getId(),anexo)));

        ComentarioOutputDTO comentarioOutputDTO = mapper.mapTo(comentario,ComentarioOutputDTO.class);
        comentarioOutputDTO.setAnexos(anexos);

        return comentarioOutputDTO;
    }

    @Override
    public List<ComentarioOutputDTO> buscarPorTicketId(Long ticketId) {
        List<ComentarioOutputDTO> comentarioOutputDTOList = mapper.toList(comentarioRepository.findByTicketId(ticketId), ComentarioOutputDTO.class);
        comentarioOutputDTOList.forEach(comentarioOutputDTO -> comentarioOutputDTO.setAnexos(buscarAnexos(comentarioOutputDTO.getId())));
        return comentarioOutputDTOList;
    }

    @Override
    public void deletarPorId(Long comentarioId) {
        comentarioRepository.deleteById(comentarioId);
        List<FileResponse> anexoList = buscarAnexos(comentarioId);
        anexoList.forEach(anexo -> deletarAnexo(comentarioId,anexo.getFileName()));
    }

    public FileResponse salvarAnexo(Long comentarioId, MultipartFile file) {
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

            return new FileResponse(originalFilename,contentType,size,downloadUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FileResponse> buscarAnexos(Long comentarioId) {
        List<FileResponse> photos = new ArrayList<>();
        try {
            Path roomPath = Paths.get(getAbsolutePath()+uploadDir).resolve(comentarioId.toString());
            try (Stream<Path> filePaths = Files.list(roomPath)) {
                filePaths.forEach(file -> {
                    try {
                        Resource resource = new UrlResource(file.toUri());
                        if (resource.exists() || resource.isReadable()) {
                            String contentType = Files.probeContentType(file);
                            if (contentType == null) {
                                contentType = "application/octet-stream";
                            }
                            long sizeInBytes = Files.size(file);
                            String size = formatSize(sizeInBytes);
                            String originalFilename = resource.getFilename();
                            String downloadUri = "/api/v1/comentarios/" + comentarioId + "/download-anexo/"+originalFilename;
                            photos.add(new FileResponse(originalFilename,contentType,size,downloadUri));
                        }
                    } catch (Exception e) {
                        System.out.println("Erro processando anexo " + file.toString());
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao recuperar anexos do comentário:" + comentarioId, e);
        }
        return photos;
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
