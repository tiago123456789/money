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
}
