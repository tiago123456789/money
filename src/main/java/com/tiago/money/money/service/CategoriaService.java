package com.tiago.money.money.service;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.model.Categoria;
import com.tiago.money.money.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> searchAll() {
        return this.categoriaRepository.findAll();
    }

    public Categoria findOne(Long id) throws NaoEncontradoException {
        Optional<Categoria> optionalCategoria = this.categoriaRepository.findById(id);

        if (! optionalCategoria.isPresent()) {
            throw new NaoEncontradoException("Categoria não encontrada!");
        }

        return optionalCategoria.get();
    }

    public Categoria save(Categoria newCategoria) {
        return this.categoriaRepository.save(newCategoria);
    }
}
