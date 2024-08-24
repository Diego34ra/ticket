package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoInputDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoOutputDTO;
import br.edu.ifgoiano.ticket.model.Departamento;
import br.edu.ifgoiano.ticket.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping
    public ResponseEntity<DepartamentoOutputDTO> criar(@RequestBody DepartamentoInputDTO departamentoDTO){
        var departamentoCriado = departamentoService.criar(departamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoCriado);
    }

    @GetMapping
    public ResponseEntity<List<DepartamentoOutputDTO>> buscarTodos(){
        var departamentoList = departamentoService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(departamentoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartamentoOutputDTO> buscarPorId(@PathVariable Long id){
        var departamento = departamentoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(departamento);
    }

    @PutMapping("{id}")
    public ResponseEntity<DepartamentoOutputDTO> atualizar(@PathVariable Long id, @RequestBody DepartamentoInputDTO departamentoDTO){
        var departamentoAtualizado = departamentoService.atualizar(id,departamentoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(departamentoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable Long id){
        departamentoService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
