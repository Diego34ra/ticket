package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.request.EmailTicketDTO;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.producer.TicketProducer;
import br.edu.ifgoiano.ticket.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private TicketProducer ticketProducer;

    @Override
    public void enviarEmailTicket(Ticket ticket) {
        String assunto = "Ticket #" + ticket.getId() + " Criado com Sucesso";
        String texto =    "Olá [cliente],\n\n"
                        + "Seu ticket foi criado com sucesso e está sendo processado.\n\n"
                        + "Detalhes do Ticket:\n"
                        + "- Número do Ticket: " + ticket.getId() + "\n"
                        + "- Descrição: " + ticket.getDescricao() + "\n\n"
                        + "Nossa equipe entrará em contato com você em breve para fornecer mais informações.\n\n"
                        + "Atenciosamente,\nNome da Empresa";
        EmailTicketDTO emailTicketDTO = new EmailTicketDTO(ticket.getId(),
                "jose.ribeiro@estudante.ifgoiano.edu.br",assunto,texto);
        ticketProducer.publishMessageEmail(emailTicketDTO);
    }
}
