package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRespository extends JpaRepository<Ticket,Long>, JpaSpecificationExecutor<Ticket> {

    List<Ticket> findByClienteId(Long clienteId);

    List<Ticket> findByResponsavelId(Long responsavelId);

    List<Ticket> findByDepartamentoGerenteId(Long gerenteId);
}
