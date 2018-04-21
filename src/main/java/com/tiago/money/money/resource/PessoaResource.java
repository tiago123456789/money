package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<Pessoa>> buscarTodas() {
        return ResponseEntity.ok().body(this.pessoaService.buscarTodas());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = this.pessoaService.salvar(pessoa);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        this.pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id,  @Valid @RequestBody Pessoa pessoa) {
        this.pessoaService.atualizar(id, pessoa);
        return ResponseEntity.noContent().build();
    }
}