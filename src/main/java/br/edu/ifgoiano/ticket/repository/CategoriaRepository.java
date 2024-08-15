package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}
