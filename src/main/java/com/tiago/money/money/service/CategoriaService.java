package com.tiago.money.money.service;

import com.tiago.money.money.model.Categoria;
import com.tiago.money.money.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> searchAll() {
        return this.categoriaRepository.findAll();
    }

    public Categoria findOne(Long id) throws Exception {
        Categoria category = this.categoriaRepository.getOne(id);

        if (category == null) {
            throw new Exception("Category not found!");
        }

        return category;
    }

    public Categoria save(Categoria newCategoria) {
        return this.categoriaRepository.save(newCategoria);
    }
}
