package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.regraPrioridade.RegraPrioridadeOutputDTO;
import br.edu.ifgoiano.ticket.service.RegraPrioridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/regraPrioridades")
public class RegraPrioridadeController {


    @Autowired
    private RegraPrioridadeService regraPrioridadeService;

    @PostMapping
    public ResponseEntity<RegraPrioridadeOutputDTO> criar(@RequestBody RegraPrioridadeInputDTO regraPrioridadeInputDTO){
        var regraPrioridadeCriada = regraPrioridadeService.criar(regraPrioridadeInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regraPrioridadeCriada);
    }

    @GetMapping
    public ResponseEntity<List<RegraPrioridadeOutputDTO>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(regraPrioridadeService.buscarTodos());
    }

    @PutMapping("{id}")
    public ResponseEntity<RegraPrioridadeOutputDTO> atualizar(@PathVariable Long id, @RequestBody RegraPrioridadeInputDTO regraPrioridadeInputDTO){
        var regraPrioridadeAtualizada = regraPrioridadeService.atualizar(id,regraPrioridadeInputDTO);
        return ResponseEntity.status(HttpStatus.OK).body(regraPrioridadeAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id){
        regraPrioridadeService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
