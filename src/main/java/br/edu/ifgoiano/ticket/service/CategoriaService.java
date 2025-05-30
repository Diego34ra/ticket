package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.categoria.CategoriaDTO;
import br.edu.ifgoiano.ticket.model.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria criar(CategoriaDTO categoriaDTO);

    List<Categoria> buscarTodos();

    Categoria buscaPorId(Long id);

    Categoria atualizar(Long id, CategoriaDTO departamento);

    void deletePorId(Long id);
}
