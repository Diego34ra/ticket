package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.TicketHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketHistoricoRepository extends JpaRepository<TicketHistorico,Long> {
}