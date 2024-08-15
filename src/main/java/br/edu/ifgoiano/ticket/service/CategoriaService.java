package br.edu.ifgoiano.ticket.service;

import br.edu.ifgoiano.ticket.controller.dto.request.CategoriaDTO;
import br.edu.ifgoiano.ticket.controller.dto.request.DepartamentoDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.model.Departamento;

import java.util.List;

public interface CategoriaService {

    Categoria criar(CategoriaDTO categoriaDTO);

    List<Categoria> buscarTodos();

    Categoria buscaPorId(Long id);

    Categoria atualizar(Long id, CategoriaDTO departamento);

    void deletePorId(Long id);
}
