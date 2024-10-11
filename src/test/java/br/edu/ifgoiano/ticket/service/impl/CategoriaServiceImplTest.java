package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
class CategoriaServiceImplTest {

    @Mock
    private CategoriaService categoriaService;

    @Test
    void criar() {
        when(categoriaService.buscaPorId(1L)).thenReturn(new Categoria(1L,"Categoria Teste","Testando categoria."));

        Categoria resultado = categoriaService.buscaPorId(1L);

        assertThat(resultado.getNome()).isEqualTo("Categoria Teste");
    }

    @Test
    void buscarTodos() {
    }

    @Test
    void atualizar() {
    }

    @Test
    void deletePorId() {
    }
}