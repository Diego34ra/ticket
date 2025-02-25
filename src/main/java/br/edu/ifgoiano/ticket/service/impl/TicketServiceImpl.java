package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketSimpleResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.*;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;
import br.edu.ifgoiano.ticket.model.Ticket;
import br.edu.ifgoiano.ticket.repository.TicketRespository;
import br.edu.ifgoiano.ticket.repository.specification.TicketSpecification;
import br.edu.ifgoiano.ticket.service.*;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import com.opencsv.CSVWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRespository ticketRepository;

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
    public TicketResponseDTO criar(TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = mapper.mapTo(ticketRequestDTO, Ticket.class);
        Categoria categoria = categoriaService.buscaPorId(ticket.getCategoria().getId());
        Departamento departamento = mapper.mapTo(departamentoService.buscarPorId(ticket.getDepartamento().getId()),Departamento.class);
        RegraPrioridade regraPrioridade = regraPrioridadeService.buscarPorCategoriaAndDepartamento(categoria,departamento);

        Usuario cliente;

        if(ticket.getCliente() == null || ticket.getCliente().getId() == null){
            Long uuidAuth = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            cliente = mapper.mapTo(usuarioService.buscaPorId(uuidAuth),Usuario.class);
        } else
            cliente = mapper.mapTo(usuarioService.buscaPorId(ticket.getCliente().getId()),Usuario.class);

        Usuario responsavel;

        if(ticket.getResponsavel() == null || ticket.getResponsavel().getId() == null)
            responsavel = departamento.getGerente();
        else
            responsavel = ticket.getResponsavel();

        ticket.setCliente(cliente);
        ticket.setResponsavel(responsavel);
        ticket.setDataCriacao(LocalDateTime.now());
        ticket.setStatus(StatusTicket.ABERTO);
        ticket.setCategoria(categoria);
        ticket.setDepartamento(departamento);
        ticket.setPrioridade(regraPrioridade.getPrioridade());
        ticket.setDataMaximaResolucao(ticket.getDataCriacao().plusHours(regraPrioridade.getHorasResolucao()));
        ticket = ticketRepository.save(ticket);
        emailService.enviarTicketEmail(ticket);
        return mapper.mapTo(ticket, TicketResponseDTO.class);
    }

    @Override
    public List<TicketSimpleResponseDTO> buscarTodos() {
        List<Ticket> ticketList = ticketRepository.findAll();

        return mapper.toList(ticketList, TicketSimpleResponseDTO.class);
    }

    private Set<String> getUserRoles(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    @Override
    public List<TicketSimpleResponseDTO> buscarTodosFilter(String titulo, StatusTicket status, Prioridade prioridade, String nomeResponsavel, String dataInicio, String dataFim) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uuidAuth = Long.valueOf((String) authentication.getPrincipal());
        Set<String> roles = getUserRoles(authentication);

        Specification<Ticket> spec = TicketSpecification.filterTickets(titulo, status, prioridade, nomeResponsavel,dataInicio,dataFim);

        List<Ticket> ticketList = ticketRepository.findAll(spec);

        if (roles.contains("ROLE_CLIENTE")) {
            ticketList = ticketList.stream()
                    .filter(ticket -> Objects.nonNull(ticket.getCliente()) && Objects.equals(ticket.getCliente().getId(), uuidAuth))
                    .toList();
        } else if (roles.contains("ROLE_FUNCIONARIO")) {
            ticketList = ticketList.stream()
                    .filter(ticket -> Objects.nonNull(ticket.getResponsavel()) && Objects.equals(ticket.getResponsavel().getId(), uuidAuth))
                    .toList();
        } else if (roles.contains("ROLE_GERENTE")) {
            ticketList = ticketList.stream()
                    .filter(ticket -> Objects.nonNull(ticket.getDepartamento()) && Objects.equals(ticket.getDepartamento().getGerente().getId(), uuidAuth))
                    .toList();
        }

        return mapper.toList(ticketList, TicketSimpleResponseDTO.class);
    }

    @Override
    public TicketResponseDTO buscarPorId(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uuidAuth = Long.valueOf((String) authentication.getPrincipal());
        Set<String> roles = getUserRoles(authentication);

        Optional<Ticket> ticketOptional;
        if (roles.contains("ROLE_CLIENTE")) {
            ticketOptional = ticketRepository.findByClienteIdAndId(uuidAuth,id);
        } else if (roles.contains("ROLE_FUNCIONARIO")) {
            ticketOptional = ticketRepository.findByResponsavelIdAndId(uuidAuth,id);
        } else if (roles.contains("ROLE_GERENTE")) {
            ticketOptional = ticketRepository.findByDepartamentoGerenteIdAndId(uuidAuth,id);
        } else
            ticketOptional = ticketRepository.findById(id);

        Ticket ticket = ticketOptional.orElseThrow(() ->
                new ResourceNotFoundException("Não foi encontrado nenhum ticket com esse id.")
        );

        return mapper.mapTo(ticket, TicketResponseDTO.class);
    }

    @Override
    public TicketResponseDTO atualizar(Long id, TicketRequestUpdateDTO ticketRequestUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uuidAuth = Long.valueOf((String) authentication.getPrincipal());
        Set<String> roles = getUserRoles(authentication);

        Optional<Ticket> ticketOptional;
        if (roles.contains("ROLE_CLIENTE")) {
            ticketOptional = ticketRepository.findByClienteIdAndId(uuidAuth,id);
        } else if (roles.contains("ROLE_FUNCIONARIO")) {
            ticketOptional = ticketRepository.findByResponsavelIdAndId(uuidAuth,id);
        } else if (roles.contains("ROLE_GERENTE")) {
            ticketOptional = ticketRepository.findByDepartamentoGerenteIdAndId(uuidAuth,id);
        } else
            ticketOptional = ticketRepository.findById(id);

        Ticket ticket = ticketOptional.orElseThrow(() ->
                new ResourceNotFoundException("Não foi encontrado nenhum ticket com esse id.")
        );

        Map<String, Map<String, Object>> camposAlterados = new HashMap<>();

        checkAndRecordEntityChange("categoria", ticket.getCategoria(), ticketRequestUpdateDTO.getCategoria(), camposAlterados);
        checkAndRecordEntityChange("departamento", ticket.getDepartamento(), ticketRequestUpdateDTO.getDepartamento(), camposAlterados);
        checkAndRecordEntityChange("responsavel", ticket.getResponsavel(), ticketRequestUpdateDTO.getResponsavel(), camposAlterados);
        List<String> variaveisIgnoradas = Arrays.asList("comentarios", "id", "categoria", "departamento", "responsavel", "cliente", "class", "historicos", "registroTrabalhos");
        BeanWrapper wrapper = new BeanWrapperImpl(ticket);
        for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
            String nomeVariavel = descriptor.getName();
            if (!variaveisIgnoradas.contains(nomeVariavel)) {
                Object antigoValor = wrapper.getPropertyValue(nomeVariavel);
                Object novoValor = new BeanWrapperImpl(ticketRequestUpdateDTO).getPropertyValue(nomeVariavel);

                if (novoValor != null && !novoValor.equals(antigoValor)) {
                    Map<String, Object> values = new HashMap<>();
                    values.put("antigoValor", antigoValor);
                    values.put("novoValor", novoValor);
                    camposAlterados.put(nomeVariavel, values);
                }
            }
        }

        Usuario usuarioResponsavelPelaAtualizacao = mapper.mapTo(usuarioService.buscaPorId(uuidAuth), Usuario.class);

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
                    ticketHistorico.setAgente(usuarioResponsavelPelaAtualizacao);
                    ticketHistoricoList.add(ticketHistorico);
                }
        );
        ticket.getHistoricos().addAll(ticketHistoricoList);
        ticketHistoricoList.forEach(ticketHistorico -> ticketHistoricoService.criar(ticketHistorico));

        BeanUtils.copyProperties(ticketRequestUpdateDTO, ticket, objectUtils.getNullPropertyNames(ticketRequestUpdateDTO));

        var ticketSalvado = ticketRepository.save(ticket);

        if(ticketSalvado.getStatus() == StatusTicket.FINALIZADO)
            emailService.enviarTicketFinalizadoEmail(ticketSalvado);

        return mapper.mapTo(ticketSalvado, TicketResponseDTO.class);
    }

    @Override
    public Ticket atualizaTicketEmAndamento(Usuario usuario, Ticket ticket){
        String valorAntigo = ticket.getStatus().toString();
        ticket.setStatus(StatusTicket.EM_ANDAMENTO);

        List<TicketHistorico> ticketHistoricoList = new ArrayList<>();
        TicketHistorico ticketHistorico = new TicketHistorico();
        ticketHistorico.setCampo("status");
        ticketHistorico.setUltimoValor(valorAntigo);
        ticketHistorico.setNovoValor(ticket.getStatus().toString());
        ticketHistorico.setDataAlteracao(LocalDateTime.now());
        ticketHistorico.setTicket(ticket);
        ticketHistorico.setAgente(usuario);
        ticketHistoricoList.add(ticketHistorico);

        ticket.getHistoricos().addAll(ticketHistoricoList);
        ticketHistoricoList.forEach(ticketHistoricoCriar -> ticketHistoricoService.criar(ticketHistoricoCriar));

        // Enviar email para o cliente

        return ticketRepository.save(ticket);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uuidAuth = Long.valueOf((String) authentication.getPrincipal());
        Set<String> roles = getUserRoles(authentication);

        Optional<Ticket> ticketOptional;
        if (roles.contains("ROLE_CLIENTE")) {
            ticketOptional = ticketRepository.findByClienteIdAndId(uuidAuth,id);
        } else if (roles.contains("ROLE_FUNCIONARIO")) {
            ticketOptional = ticketRepository.findByResponsavelIdAndId(uuidAuth,id);
        } else if (roles.contains("ROLE_GERENTE")) {
            ticketOptional = ticketRepository.findByDepartamentoGerenteIdAndId(uuidAuth,id);
        } else
            ticketOptional = ticketRepository.findById(id);

        if(ticketOptional.isPresent())
            ticketRepository.deleteById(id);
    }

    @Override
    public ByteArrayInputStream generateCsvReportByDate(String dataInicio, String dataFim) {
        List<TicketSimpleResponseDTO> ticketsList = buscarTodosFilter(null,null,null,null,dataInicio,dataFim);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            String[] header = { "ID", "Título", "Descrição", "Status", "Prioridade","Data de Criação" };
            writer.writeNext(header);

            for (TicketSimpleResponseDTO ticket : ticketsList) {
                String[] data = {
                        String.valueOf(ticket.getId()),
                        ticket.getTitulo(),
                        ticket.getDescricao(),
                        String.valueOf(ticket.getStatus()),
                        String.valueOf(ticket.getPrioridade()),
                        String.valueOf(ticket.getDataCriacao())
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
