package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.CategoriaDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.repository.CategoriaRepository;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private MyModelMapper mapper;

    @Test
    void criar() {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNome("Categoria Teste");
        categoriaDTO.setDescricao("Categoria para realizar teste unitário");

        Categoria categoria = new Categoria();
        categoria.setNome("Categoria Teste");
        categoria.setDescricao("Categoria para realizar teste unitário");

        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setId(1L);
        categoriaCriada.setNome("Categoria Teste");
        categoriaCriada.setDescricao("Categoria para realizar teste unitário");

        when(mapper.mapTo(categoriaDTO, Categoria.class)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoriaCriada);

        Categoria resultado = categoriaService.criar(categoriaDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Categoria Teste", resultado.getNome());
        assertEquals("Categoria para realizar teste unitário", resultado.getDescricao());
    }


    @Test
    void buscarTodos() {
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNome("Categoria Teste 1");
        categoria1.setDescricao("Categoria Teste 1");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Categoria Teste 2");
        categoria2.setDescricao("Categoria Teste 2");

        List<Categoria> categoriaList = new ArrayList<>();
        categoriaList.add(categoria1);
        categoriaList.add(categoria2);

        when(categoriaRepository.findAll()).thenReturn(categoriaList);

        List<Categoria> resultados = categoriaService.buscarTodos();

        assertNotNull(resultados);;
        assertEquals(resultados.size(),2);
        assertEquals(categoria1.getId(), resultados.get(0).getId(), "O ID da primeira categoria não corresponde.");
        assertEquals(categoria2.getId(), resultados.get(1).getId(), "O ID da segunda categoria não corresponde.");
    }

    @Test
    void atualizar() {
    }

    @Test
    void deletePorId() {
    }
}