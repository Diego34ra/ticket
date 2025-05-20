package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.model.Anexo;
import br.edu.ifgoiano.ticket.model.Comentario;
import br.edu.ifgoiano.ticket.repository.AnexoRepository;
import br.edu.ifgoiano.ticket.service.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Service
public class AnexoServiceImpl implements AnexoService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private AnexoRepository anexoRepository;

    @Override
    public List<Anexo> salvarAnexos (Comentario comentario, MultipartFile [] files){
        List<Anexo> anexoList = new ArrayList<>();
        if(files != null && files.length > 0) {
            Arrays.stream(files).forEach(anexo -> anexoList.add(salvarAnexo(comentario, anexo)));
            anexoList.forEach(anexo -> anexo.setComentario(comentario));
            return anexoRepository.saveAll(anexoList);
        }
        return null;
    }

    public Anexo salvarAnexo(Comentario comentario, MultipartFile file) {
        try {
            Path uploadPath = Paths.get(getAbsolutePath() + uploadDir, comentario.getId().toString());
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String originalFilename = file.getOriginalFilename();
            String contentType = Files.probeContentType(filePath);
            long sizeInBytes = Files.size(filePath);
            String size = formatSize(sizeInBytes);
            String downloadUri = "/api/v1/comentarios/" + comentario.getId() + "/download-anexo/"+originalFilename;

            return new Anexo(originalFilename,contentType,size,downloadUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletarAnexos (Comentario comentario){
        comentario.getAnexos().forEach(anexo -> deletarAnexo(comentario.getId(), anexo.getNomeArquivo()));
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

    public String getAbsolutePath() {
        String userDir = System.getProperty("user.dir");
        Path srcPath = Paths.get(userDir);
        return srcPath.toAbsolutePath().toString();
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
