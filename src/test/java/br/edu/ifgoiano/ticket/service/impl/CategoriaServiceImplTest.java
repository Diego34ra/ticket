package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.CategoriaDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.repository.CategoriaRepository;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private MyModelMapper mapper;

    @Mock
    private ObjectUtils objectUtils;

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
        Long id = 1L;
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(id);
        categoriaExistente.setNome("Categoria");

        CategoriaDTO categoriaRequestAtualizar = new CategoriaDTO();
        categoriaRequestAtualizar.setNome("Categoria Atualizada");

        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setId(id);
        categoriaAtualizada.setNome("Categoria Atualizada");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaExistente));
        when(objectUtils.getNullPropertyNames(categoriaRequestAtualizar)).thenReturn(new String[]{});
        when(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaAtualizada);

        Categoria resultado = categoriaService.atualizar(id, categoriaRequestAtualizar);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Categoria Atualizada", resultado.getNome());

        verify(categoriaRepository).findById(id);
        verify(categoriaRepository).save(categoriaExistente);
        verify(objectUtils).getNullPropertyNames(categoriaRequestAtualizar);
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        Long id = 1L;
        CategoriaDTO categoriaUpdate = new CategoriaDTO();
        categoriaUpdate.setNome("Teste");

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoriaService.atualizar(id, categoriaUpdate);
        });

        assertEquals("Não foi encontrada nenhuma categoria com esse id.", exception.getMessage());

        verify(categoriaRepository).findById(id);
        verifyNoInteractions(objectUtils);
        verifyNoMoreInteractions(categoriaRepository);
    }

    @Test
    void deletePorId() {
        Long id = 1L;

        categoriaService.deletePorId(id);

        verify(categoriaRepository).deleteById(id);
    }
}