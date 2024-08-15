package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegraPrioridadeRepository extends JpaRepository<RegraPrioridade,Long> {
    Optional<RegraPrioridade> findByCategoriaAndDepartamento(Categoria categoria, Departamento departamento);
}
