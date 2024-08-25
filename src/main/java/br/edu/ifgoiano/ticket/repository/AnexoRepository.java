package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo,Long> {
}
