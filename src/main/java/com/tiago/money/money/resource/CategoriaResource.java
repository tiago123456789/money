package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Categoria;
import com.tiago.money.money.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Categoria> searchAll() {
        return this.categoriaService.searchAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Categoria newCategoria, HttpServletResponse response) {
        Categoria categoriaSalva = this.categoriaService.save(newCategoria);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
