package br.edu.ifgoiano.ticket.controller.dto.request.ticket;

import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.usuario.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketSimpleOutputDTO {
    private String id;
    private String titulo;
    private String descricao;
    private StatusTicket status;
    private Prioridade prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private LocalDateTime dataMaximaResolucao;
    private Categoria categoria;
    private DepartamentoSimpleOutputDTO departamento;
    private UsuarioOutputDTO cliente;
    private UsuarioOutputDTO responsavel;
}
