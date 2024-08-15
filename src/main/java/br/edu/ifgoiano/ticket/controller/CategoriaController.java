package br.edu.ifgoiano.ticket.controller;

import br.edu.ifgoiano.ticket.controller.dto.request.CategoriaDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> criar(CategoriaDTO categoriaDTO){
        var categoriaCriada = categoriaService.criar(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> buscarTodos(){
        var categoriaList = categoriaService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(categoriaList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id){
        var categoria = categoriaService.buscaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @PutMapping("{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, CategoriaDTO categoriaDTO){
        var categoriaAtualizada = categoriaService.atualizar(id,categoriaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Categoria> deletarPorId(@PathVariable Long id){
        categoriaService.deletePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
