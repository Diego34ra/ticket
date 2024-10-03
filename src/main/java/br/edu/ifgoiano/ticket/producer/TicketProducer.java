package br.edu.ifgoiano.ticket.producer;

import br.edu.ifgoiano.ticket.controller.dto.request.EmailTicketDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(EmailTicketDTO emailDTO){
        rabbitTemplate.convertAndSend("",routingKey,emailDTO);
    }
}
