package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import com.tiago.money.money.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<Page<Lancamento>> findALl(LancamentoFilter filter, org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok().body(this.lancamentoService.findAll(filter, pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Lancamento> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.lancamentoService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo  = this.lancamentoService.save(lancamento);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.lancamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
