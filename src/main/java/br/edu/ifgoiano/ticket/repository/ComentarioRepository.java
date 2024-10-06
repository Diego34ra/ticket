package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario,Long> {
    List<Comentario> findByTicketId(Long ticketId);
}
