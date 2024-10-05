package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketInputUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.*;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.service.*;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

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
    private TicketHistoricoService ticketHistoricoService;

    @Autowired
    private RegraPrioridadeService regraPrioridadeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Override
    public TicketOutputDTO criar(TicketInputDTO ticketInputDTO) {
        Ticket ticket = mapper.mapTo(ticketInputDTO, Ticket.class);
        Categoria categoria = categoriaService.buscaPorId(ticket.getCategoria().getId());
        Departamento departamento = mapper.mapTo(departamentoService.buscarPorId(ticket.getDepartamento().getId()),Departamento.class);
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
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("responsável pela requisicao = "+ user.getId() + " - "+user.getFirstName());
        ticket = ticketRespository.save(ticket);
        emailService.enviarTicketEmail(ticket);
        return mapper.mapTo(ticket, TicketOutputDTO.class);
    }

    @Override
    public List<TicketSimpleOutputDTO> buscarTodos() {
        return mapper.toList(ticketRespository.findAll(),TicketSimpleOutputDTO.class);
    }

    @Override
    public TicketOutputDTO buscarPorId(Long id) {
        Ticket ticket = ticketRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum ticket com esse id."));
        return mapper.mapTo(ticket,TicketOutputDTO.class);
    }

    @Override
    public TicketOutputDTO atualizar(Long id, TicketInputUpdateDTO ticketInputUpdateDTO) {
        Ticket ticket = ticketRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum ticket com esse id."));

        Map<String, Map<String, Object>> camposAlterados = new HashMap<>();

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("responsável pela requisicao = "+ user.getId() + " - "+user.getFirstName());

        checkAndRecordEntityChange("categoria", ticket.getCategoria(), ticketInputUpdateDTO.getCategoria(), camposAlterados);
        checkAndRecordEntityChange("departamento", ticket.getDepartamento(), ticketInputUpdateDTO.getDepartamento(), camposAlterados);
        checkAndRecordEntityChange("responsavel", ticket.getResponsavel(), ticketInputUpdateDTO.getResponsavel(), camposAlterados);
        List<String> ignoredProperties = Arrays.asList("comentarios", "id", "categoria", "departamento", "responsavel", "cliente", "class", "historicos", "registroTrabalhos");
        BeanWrapper wrapper = new BeanWrapperImpl(ticket);
        for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
            String propertyName = descriptor.getName();
            if (!ignoredProperties.contains(propertyName)) {
                Object antigoValor = wrapper.getPropertyValue(propertyName);
                Object novoValor = new BeanWrapperImpl(ticketInputUpdateDTO).getPropertyValue(propertyName);

                if (novoValor != null && !novoValor.equals(antigoValor)) {
                    Map<String, Object> values = new HashMap<>();
                    values.put("antigoValor", antigoValor);
                    values.put("novoValor", novoValor);
                    camposAlterados.put(propertyName, values);
                }
            }
        }
        List<TicketHistorico> ticketHistoricoList = new ArrayList<>();
        camposAlterados.forEach((campo, valores) -> {
                    Object antigoValor = valores.get("antigoValor");
                    Object novoValor = valores.get("novoValor");
                    TicketHistorico ticketHistorico = new TicketHistorico();
                    ticketHistorico.setCampo(campo);
                    ticketHistorico.setUltimoValor(antigoValor.toString());
                    ticketHistorico.setNovoValor(novoValor.toString());
                    ticketHistorico.setDataAlteracao(LocalDateTime.now());
                    ticketHistorico.setTicket(ticket);
                    ticketHistorico.setAgente(ticket.getResponsavel()); // alterar depois para a forma correta
                    ticketHistoricoList.add(ticketHistorico);
                }
        );
        ticket.setHistoricos(ticketHistoricoList);
        ticketHistoricoList.forEach(ticketHistorico -> ticketHistoricoService.criar(ticketHistorico));

        BeanUtils.copyProperties(ticketInputUpdateDTO, ticket, objectUtils.getNullPropertyNames(ticketInputUpdateDTO));

        var ticketSalvado = ticketRespository.save(ticket);

        if(ticketSalvado.getStatus() == StatusTicket.FINALIZADO)
            emailService.enviarTicketFinalizadoEmail(ticketSalvado);

        return mapper.mapTo(ticketSalvado, TicketOutputDTO.class);
    }

    private <T> void checkAndRecordEntityChange(String fieldName, T currentEntity, T newEntity, Map<String, Map<String, Object>> alteredFields) {
        Long currentId = getEntityId(currentEntity);
        Long newId = getEntityId(newEntity);

        if (!Objects.equals(currentId, newId)) {
            Map<String, Object> values = new HashMap<>();
            values.put("antigoValor", currentId);
            values.put("novoValor", newId);
            alteredFields.put(fieldName, values);
        }
    }

    private Long getEntityId(Object entity) {
        if (entity == null) return null;
        try {
            Method getIdMethod = entity.getClass().getMethod("getId");
            return (Long) getIdMethod.invoke(entity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Erro ao obter o ID da entidade", e);
        }
    }

    @Override
    public void deletePorId(Long id) {
        ticketRespository.deleteById(id);
    }
}
