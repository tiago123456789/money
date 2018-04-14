package com.tiago.money.money.resource;

import com.tiago.money.money.model.Categoria;
import com.tiago.money.money.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> searchAll() {
        return this.categoriaService.searchAll();
    }
}
