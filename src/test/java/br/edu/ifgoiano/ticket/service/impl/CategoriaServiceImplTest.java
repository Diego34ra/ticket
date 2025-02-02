package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.categoria.CategoriaDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.repository.CategoriaRepository;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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
    void criar_deveCriarCategoriaComSucesso() {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        Categoria categoria = new Categoria();
        Categoria categoriaCriada = new Categoria();
        when(mapper.mapTo(any(CategoriaDTO.class), eq(Categoria.class))).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoriaCriada);

        Categoria resultado = categoriaService.criar(categoriaDTO);

        assertNotNull(resultado);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void buscar_deveBuscarUmaListaDeCategoria() {
        when(categoriaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Categoria> resultados = categoriaService.buscarTodos();

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
        verify(categoriaRepository).findAll();
    }

    @Test
    void atualizar_deveAtualizarCategoriaComSucesso() {
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
    void atualizar_deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
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
    void delete_deveDeletarCategoriaComSucesso() {
        Long id = 1L;

        categoriaService.deletePorId(id);

        verify(categoriaRepository).deleteById(id);
    }
}