package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.ticket.TicketRequestUpdateDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.ticket.TicketSimpleResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ErrorDetails;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;
import br.edu.ifgoiano.ticket.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
@Tag(name = "Ticket")
@Slf4j
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @Operation(summary = "Criar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket criado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "ticketCache", allEntries = true)
    public ResponseEntity<TicketResponseDTO> criar(@RequestBody TicketRequestDTO ticket){
        var ticketCriado = ticketService.criar(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar todas os tickets")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tickets buscados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TicketSimpleResponseDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @Cacheable(value = "ticketCache")
    public ResponseEntity<List<TicketSimpleResponseDTO>> buscarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) StatusTicket status,
            @RequestParam(required = false) Prioridade prioridade,
            @RequestParam(required = false) String responsavel,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim){
        log.info("Buscando tickets");
        var ticketList = ticketService.buscarTodosFilter(titulo, status, prioridade, responsavel, dataInicio,dataFim);
        return ResponseEntity.status(HttpStatus.OK).body(ticketList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria buscada com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<TicketResponseDTO> buscarPorId(@PathVariable Long id){
        var ticket = ticketService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket atualizado com sucesso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "ticketCache", allEntries = true)
    public ResponseEntity<TicketResponseDTO> atualizar(@PathVariable Long id, @RequestBody TicketRequestUpdateDTO ticketRequestUpdateDTO){
        var ticketAtualizado = ticketService.atualizar(id, ticketRequestUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ticketAtualizado);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar um ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket deletado com sucesso."),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    @CacheEvict(value = "ticketCache", allEntries = true)
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        ticketService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("report/csv")
    @Operation(summary = "Gerar Relatório de tickets")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso."),
            @ApiResponse(responseCode = "401", description = "O token de autorização está ausente ou é inválido.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "403", description = "Acesso negado.Você não tem permissão para acessar este recurso.",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})
    })
    public ResponseEntity<InputStreamResource> generateCsvReportByDate(
            @RequestParam String dataInicio,
            @RequestParam String dataFim)  {

        ByteArrayInputStream byteArrayInputStream = ticketService.generateCsvReportByDate(dataInicio,dataFim);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ticket_report_" + dataInicio + "_to_" + dataFim + ".csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(byteArrayInputStream));
    }
}
