package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento,Long> {
}
