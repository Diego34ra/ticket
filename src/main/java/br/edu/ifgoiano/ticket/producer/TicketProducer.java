package br.edu.ifgoiano.ticket.producer;

import br.edu.ifgoiano.ticket.controller.dto.request.EmailDTO;
import br.edu.ifgoiano.ticket.model.Usuario;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketProducer {

    final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public TicketProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessageEmail(EmailDTO emailDTO){
        System.out.println(routingKey);
        rabbitTemplate.convertAndSend("",routingKey,emailDTO);
    }
}
