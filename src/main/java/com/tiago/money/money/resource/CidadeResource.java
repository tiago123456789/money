package com.tiago.money.money.resource;

import com.tiago.money.money.bo.CidadeBO;
import com.tiago.money.money.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeResource {

    @Autowired
    private CidadeBO cidadeBO;

    @GetMapping(value = "/")
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<List<Cidade>> searchCitiesStateSpecified(@RequestParam Long idEstado) {
        return ResponseEntity.ok(this.cidadeBO.searchCitiesInStateSpecified(idEstado));
    }
}
