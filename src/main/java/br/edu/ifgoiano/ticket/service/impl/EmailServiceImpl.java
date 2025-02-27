package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.request.email.EmailRequestDTO;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.producer.TicketProducer;
import br.edu.ifgoiano.ticket.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private TicketProducer ticketProducer;

    @Override
    public void enviarTicketEmail(Ticket ticket) {
        String assunto = "Ticket #" + ticket.getId() + " Criado com Sucesso";
        String texto =    "Olá "+ticket.getCliente().getNome()+",\n\n"
                        + "Seu ticket foi criado com sucesso e está sendo processado.\n\n"
                        + "Detalhes do Ticket:\n"
                        + "- Número do Ticket: " + ticket.getId() + "\n"
                        + "- Descrição: " + ticket.getDescricao() + "\n\n"
                        + "Nossa equipe entrará em contato com você em breve para fornecer mais informações.\n\n";
        EmailRequestDTO emailRequestDTO = new EmailRequestDTO(ticket.getCliente().getEmail(),assunto,texto);
        ticketProducer.publishMessageEmail(emailRequestDTO);
    }

    @Override
    public void enviarTicketEmAndamentoEmail(Ticket ticket) {
        String assunto = "Ticket #" + ticket.getId() + " Em Andamento";
        String texto =    "Olá "+ticket.getCliente().getNome()+",\n" +
                "\n" +
                "Gostaríamos de informar que o seu ticket " + ticket.getId() + " está em processo de análise e tratamento pela nossa equipe. Abaixo estão os detalhes:\n" +
                "\n" +
                "ID do Ticket: " + ticket.getId() + "\n" +
                "Assunto: "+ticket.getTitulo()+"\n" +
                "\n" +
                "Nossa equipe está trabalhando para resolver sua solicitação o mais breve possível.\n" +
                "\n" +
                "Agradecemos pela sua compreensão e confiança em nossos serviços.\n";

        EmailRequestDTO emailRequestDTO = new EmailRequestDTO(ticket.getCliente().getEmail(),assunto,texto);
        ticketProducer.publishMessageEmail(emailRequestDTO);
    }

    @Override
    public void enviarTicketFinalizadoEmail(Ticket ticket) {
        String assunto = "Ticket #" + ticket.getId() + " Finalizado";
        String texto =    "Olá "+ticket.getCliente().getNome()+",\n" +
                "\n" +
                "Gostaríamos de informar que o seu ticket " + ticket.getId() + " foi finalizado com sucesso. Abaixo estão os detalhes:\n" +
                "\n" +
                "ID do Ticket: " + ticket.getId() + "\n" +
                "Assunto: "+ticket.getTitulo()+"\n" +
                "\n" +
                "Se você tiver qualquer dúvida ou precisar de mais assistência, não hesite em entrar em contato conosco. Estamos à disposição para ajudar!\n" +
                "\n" +
                "Agradecemos por utilizar nossos serviços.\n";
        EmailRequestDTO emailRequestDTO = new EmailRequestDTO(ticket.getCliente().getEmail(),assunto,texto);
        ticketProducer.publishMessageEmail(emailRequestDTO);
    }

    @Override
    public void enviarUsuarioCadastradoEmail(Usuario usuario) {
        String assunto = "Cadastro Realizado com Sucesso";
        String texto =    "Olá "+usuario.getNome()+",\n" +
                "\n" +
                "É com grande satisfação que informamos que seu cadastro em nosso sistema foi realizado com sucesso. Agora você tem acesso a todas as funcionalidades disponíveis.\n" +
                "\n" +
                "Se precisar de ajuda para acessar sua conta ou tiver alguma dúvida, sinta-se à vontade para entrar em contato conosco.\n" +
                "\n" +
                "Estamos felizes em tê-lo conosco!\n";
        EmailRequestDTO emailRequestDTO = new EmailRequestDTO(usuario.getEmail(),assunto,texto);
        ticketProducer.publishMessageEmail(emailRequestDTO);
    }
}
