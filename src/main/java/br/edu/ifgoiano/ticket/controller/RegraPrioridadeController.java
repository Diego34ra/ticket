package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.RegraPrioridadeInputDTO;
import br.edu.ifgoiano.ticket.model.RegraPrioridade;
import br.edu.ifgoiano.ticket.service.RegraPrioridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/regraPrioridades")
public class RegraPrioridadeController {


    @Autowired
    private RegraPrioridadeService regraPrioridadeService;

    @PostMapping
    public ResponseEntity<RegraPrioridade> criar(@RequestBody RegraPrioridadeInputDTO regraPrioridadeInputDTO){
        var regraPrioridadeCriada = regraPrioridadeService.criar(regraPrioridadeInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regraPrioridadeCriada);
    }

}