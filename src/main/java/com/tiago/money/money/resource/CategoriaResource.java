package com.tiago.money.money.resource;

import com.tiago.money.money.model.Categoria;
import com.tiago.money.money.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Categoria newCategoria) {
        Categoria categorySaved = this.categoriaService.save(newCategoria);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(categorySaved.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(this.categoriaService.findOne(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
