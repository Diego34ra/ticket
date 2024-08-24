package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.model.RegistroTrabalho;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.RegistroTrabalhoRepository;
import br.edu.ifgoiano.ticket.service.RegistroTrabalhoService;
import br.edu.ifgoiano.ticket.service.TicketService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroTrabalhoServiceImpl implements RegistroTrabalhoService {

    @Autowired
    private RegistroTrabalhoRepository registroTrabalhoRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MyModelMapper mapper;
    @Override
    public RegistroTrabalhoOutputDTO criar(Long ticketId, RegistroTrabalhoInputDTO registroTrabalhoInputDTO) {
        Ticket ticket = mapper.mapTo(ticketService.buscarPorId(ticketId), Ticket.class);
        Usuario usuario = mapper.mapTo(usuarioService.buscaPorId(1L), Usuario.class); // altera depois
        RegistroTrabalho registroTrabalho = mapper.mapTo(registroTrabalhoInputDTO,RegistroTrabalho.class);
        registroTrabalho.setTicket(ticket);
        registroTrabalho.setAgente(usuario);

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("responsável pela requisicao = "+ user.getId() + " - "+user.getFirstName());
        return mapper.mapTo(registroTrabalhoRepository.save(registroTrabalho),RegistroTrabalhoOutputDTO.class);
    }
}
