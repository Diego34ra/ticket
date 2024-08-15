package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRespository extends JpaRepository<Ticket,Long> {
}
