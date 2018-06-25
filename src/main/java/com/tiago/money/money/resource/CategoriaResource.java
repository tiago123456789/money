package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Categoria;
import com.tiago.money.money.bo.CategoriaBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaBO categoriaBO;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA') AND #oauth2.hasScope('read') ")
    public List<Categoria> searchAll() {
        return this.categoriaBO.searchAll();
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_CATEGORIA') AND #oauth2.hasScope('write')")
    public ResponseEntity<?> save(@Valid @RequestBody Categoria newCategoria, HttpServletResponse response) {
        Categoria categoriaSalva = this.categoriaBO.save(newCategoria);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA') AND #oauth2.hasScope('read')")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(this.categoriaBO.findOne(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
