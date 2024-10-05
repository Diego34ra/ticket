package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;

public interface EmailService {
    void enviarTicketEmail(Ticket ticket);

    void enviarTicketFinalizadoEmail(Ticket ticket);

    void enviarUsuarioCadastradoEmail(Usuario usuario);
}
