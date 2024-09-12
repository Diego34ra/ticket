package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.RegistroTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroTrabalhoRepository extends JpaRepository<RegistroTrabalho,Long> {
    List<RegistroTrabalho> findByTicketId(Long ticketId);
}
