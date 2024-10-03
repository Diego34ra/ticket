package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.model.Ticket;

public interface EmailService {
    void enviarEmailTicket(Ticket ticket);
}
