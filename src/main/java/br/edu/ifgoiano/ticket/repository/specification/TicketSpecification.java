package br.edu.ifgoiano.ticket.repository.specification;

import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TicketSpecification {

    public static Specification<Ticket> filterTickets(
            String titulo,
            StatusTicket status,
            Prioridade prioridade,
            String nomeResponsavel) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (titulo != null && !titulo.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%"));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (prioridade != null) {
                predicates.add(criteriaBuilder.equal(root.get("prioridade"), prioridade));
            }

            if (nomeResponsavel != null && !nomeResponsavel.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("responsavel").get("nome"), "%" + nomeResponsavel + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
