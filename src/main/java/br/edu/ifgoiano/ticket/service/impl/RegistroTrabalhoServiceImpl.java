package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.RegistroTrabalho;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.repository.RegistroTrabalhoRepository;
import br.edu.ifgoiano.ticket.service.RegistroTrabalhoService;
import br.edu.ifgoiano.ticket.service.TicketService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroTrabalhoServiceImpl implements RegistroTrabalhoService {

    @Autowired
    private RegistroTrabalhoRepository registroTrabalhoRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectUtils objectUtils;

    @Autowired
    private MyModelMapper mapper;
    @Override
    public RegistroTrabalhoOutputDTO criar(Long ticketId, RegistroTrabalhoInputDTO registroTrabalhoInputDTO) {
        Ticket ticket = mapper.mapTo(ticketService.buscarPorId(ticketId), Ticket.class);
        Long uuidAuth = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Usuario usuario = mapper.mapTo(usuarioService.buscaPorId(uuidAuth), Usuario.class);
        RegistroTrabalho registroTrabalho = mapper.mapTo(registroTrabalhoInputDTO,RegistroTrabalho.class);
        registroTrabalho.setTicket(ticket);
        registroTrabalho.setAgente(usuario);
        return mapper.mapTo(registroTrabalhoRepository.save(registroTrabalho),RegistroTrabalhoOutputDTO.class);
    }

    @Override
    public List<RegistroTrabalhoOutputDTO> buscarTodosPorTicket(Long ticketId) {
        return mapper.toList(registroTrabalhoRepository.findByTicketId(ticketId), RegistroTrabalhoOutputDTO.class);
    }

    @Override
    public RegistroTrabalhoOutputDTO atualizar(Long registroId, RegistroTrabalhoInputUpdateDTO registroTrabalhoInputUpdateDTO) {
        RegistroTrabalho registroTrabalho = registroTrabalhoRepository.findById(registroId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum registro com esse id."));
        BeanUtils.copyProperties(registroTrabalhoInputUpdateDTO,registroTrabalho,objectUtils.getNullPropertyNames(registroTrabalhoInputUpdateDTO));
        return mapper.mapTo(registroTrabalhoRepository.save(registroTrabalho),RegistroTrabalhoOutputDTO.class);
    }

    @Override
    public void deletarPorId(Long registroId) {
        registroTrabalhoRepository.deleteById(registroId);
    }
}
