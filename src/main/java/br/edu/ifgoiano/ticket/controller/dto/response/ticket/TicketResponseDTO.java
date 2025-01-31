package br.edu.ifgoiano.ticket.controller.dto.response.ticket;

import br.edu.ifgoiano.ticket.controller.dto.response.departamento.DepartamentoSimpleResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.registroTrabalho.RegistroTrabalhoSimpleReponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.comentario.ComentarioTicketResponseDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.usuario.UsuarioSimpleResponseDTO;
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
public class TicketResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private StatusTicket status;
    private Prioridade prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private LocalDateTime dataMaximaResolucao;
    private Categoria categoria;
    private DepartamentoSimpleResponseDTO departamento;
    private UsuarioSimpleResponseDTO cliente;
    private UsuarioSimpleResponseDTO responsavel;
    private List<ComentarioTicketResponseDTO> comentarios;
    private List<TicketHistoricoResponseDTO> historicos;
    private List<RegistroTrabalhoSimpleReponseDTO> registroTrabalhos;

}
