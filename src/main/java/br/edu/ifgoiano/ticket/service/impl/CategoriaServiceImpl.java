package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.mapper.MyModelMapper;
import br.edu.ifgoiano.ticket.controller.dto.request.CategoriaDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceNotFoundException;
import br.edu.ifgoiano.ticket.model.Categoria;
import br.edu.ifgoiano.ticket.repository.CategoriaRepository;
import br.edu.ifgoiano.ticket.service.CategoriaService;
import br.edu.ifgoiano.ticket.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MyModelMapper mapper;

    @Autowired
    private ObjectUtils objectUtils;

    @Override
    @CacheEvict(value = "categoriaCache", allEntries = true)
    public Categoria criar(CategoriaDTO categoriaDTO) {
        Categoria categoria = mapper.mapTo(categoriaDTO, Categoria.class);
        Categoria categoriaCriada = categoriaRepository.save(categoria);
        return categoriaCriada;
    }

    @Override
    @Cacheable(value = "categoriaCache")
    public List<Categoria> buscarTodos() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria buscaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada nenhuma categoria com esse id."));
    }

    @Override
    @CacheEvict(value = "categoriaCache", allEntries = true)
    public Categoria atualizar(Long id, CategoriaDTO categoriaUpdate) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada nenhuma categoria com esse id."));
        BeanUtils.copyProperties(categoriaUpdate,categoria,objectUtils.getNullPropertyNames(categoriaUpdate));
        return categoriaRepository.save(categoria);
    }

    @Override
    @CacheEvict(value = "categoriaCache", allEntries = true)
    public void deletePorId(Long id) {
        categoriaRepository.deleteById(id);
    }
}
