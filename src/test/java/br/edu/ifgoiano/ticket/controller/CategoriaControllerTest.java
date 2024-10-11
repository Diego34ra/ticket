package br.edu.ifgoiano.ticket.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.edu.ifgoiano.ticket.controller.dto.request.CategoriaDTO;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void criar() throws Exception {
        when(categoriaService.criar(any(CategoriaDTO.class))).thenAnswer(invocation -> {
            CategoriaDTO dto = invocation.getArgument(0);
            return new Categoria(1L, dto.getNome(), dto.getDescricao());
        });

        Categoria novaCategoria = new Categoria();
        novaCategoria.setId(1L);
        novaCategoria.setNome("Criar Categoria Teste");
        novaCategoria.setDescricao("Teste Criando Categoria");

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaCategoria)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Criar Categoria Teste"))
                .andExpect(jsonPath("$.descricao").value("Teste Criando Categoria"));

    }

    @Test
    void buscarPorId() throws Exception {
        when(categoriaService.buscaPorId(1L)).thenReturn(new Categoria(1L, "Categoria Teste", "Teste"));
        mockMvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Categoria Teste"));
    }

    @Test
    void buscarTodos() throws Exception {
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1L, "Categoria 1", "Descrição 1"),
                new Categoria(2L, "Categoria 2", "Descrição 2")
        );

        when(categoriaService.buscarTodos()).thenReturn(categorias);

        mockMvc.perform(get("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("Categoria 1"))
                .andExpect(jsonPath("$[1].nome").value("Categoria 2"));
    }

    @Test
    void atualizar() {
    }

    @Test
    void deletarPorId() {
    }
}