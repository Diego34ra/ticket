package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRespository extends JpaRepository<Ticket,Long>, JpaSpecificationExecutor<Ticket> {

    Optional<Ticket> findByClienteIdAndId(Long clienteId, Long id);

    Optional<Ticket> findByResponsavelIdAndId(Long responsavelId, Long id);

    Optional<Ticket> findByDepartamentoGerenteIdAndId(Long gerenteId, Long id);
}
