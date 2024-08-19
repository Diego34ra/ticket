package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.*;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.service.*;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRespository ticketRespository;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegraPrioridadeService regraPrioridadeService;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Override
    public TicketOutputDTO criar(TicketInputDTO ticketInputDTO) {
        Ticket ticket = mapper.mapTo(ticketInputDTO, Ticket.class);
        Categoria categoria = categoriaService.buscaPorId(ticket.getCategoria().getId());
        Departamento departamento = departamentoService.buscarPorId(ticket.getDepartamento().getId());
        RegraPrioridade regraPrioridade = regraPrioridadeService.buscarPorCategoriaAndDepartamento(categoria,departamento);
        Usuario cliente = mapper.mapTo(usuarioService.buscaPorId(ticket.getCliente().getId()),Usuario.class);
        Usuario responsavel = mapper.mapTo(usuarioService.buscaPorId(ticket.getResponsavel().getId()),Usuario.class);
        ticket.setCliente(cliente);
        ticket.setResponsavel(responsavel);
        ticket.setDataCriacao(LocalDateTime.now());
        ticket.setStatus(StatusTicket.ABERTO);
        ticket.setCategoria(categoria);
        ticket.setDepartamento(departamento);
        ticket.setPrioridade(regraPrioridade.getPrioridade());
        ticket.setDataMaximaResolucao(ticket.getDataCriacao().plusHours(regraPrioridade.getHorasResolucao()));
        return mapper.mapTo(ticketRespository.save(ticket), TicketOutputDTO.class);
    }

    @Override
    public List<TicketOutputDTO> buscarTodos() {
        return mapper.toList(ticketRespository.findAll(),TicketOutputDTO.class);
    }

    @Override
    public TicketOutputDTO buscarPorId(Long id) {
        Ticket ticket = ticketRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum ticket com esse id."));
        return mapper.mapTo(ticket,TicketOutputDTO.class);
    }

    @Override
    public TicketOutputDTO atualizar(Long id, TicketInputDTO ticketInputDTO) {
        Ticket ticket = ticketRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum ticket com esse id."));
        BeanUtils.copyProperties(ticketInputDTO,ticket,objectUtils.getNullPropertyNames(ticketInputDTO));
        return mapper.mapTo(ticketRespository.save(ticket),TicketOutputDTO.class);
    }

    @Override
    public void deletePorId(Long id) {
        ticketRespository.deleteById(id);
    }
}
