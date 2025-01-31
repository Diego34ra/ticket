package br.edu.ifgoiano.ticket.controller.dto.request.ticket;

import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.registroTrabalho.RegistroTrabalhoSimpleOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.TicketHistoricoOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.ComentarioTicketOutputDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioSimpleOutputDTO;
import br.edu.ifgoiano.ticket.model.*;
import br.edu.ifgoiano.ticket.model.enums.Prioridade;
import br.edu.ifgoiano.ticket.model.enums.StatusTicket;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketOutputDTO {
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
    private UsuarioSimpleOutputDTO cliente;
    private UsuarioSimpleOutputDTO responsavel;
    private List<ComentarioTicketOutputDTO> comentarios;
    private List<TicketHistoricoOutputDTO> historicos;
    private List<RegistroTrabalhoSimpleOutputDTO> registroTrabalhos;

}
