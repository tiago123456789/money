package com.tiago.money.money.resource;

import com.tiago.money.money.bo.EstadoBO;
import com.tiago.money.money.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoBO estadoBO;

    @GetMapping(value = "/")
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<List<Estado>> getEstadoso() {
        return ResponseEntity.ok(this.estadoBO.getEstados());
    }
}
