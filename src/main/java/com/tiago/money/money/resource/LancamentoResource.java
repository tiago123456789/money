package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<Lancamento>> findALl() {
        return ResponseEntity.ok().body(this.lancamentoService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Lancamento> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.lancamentoService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo  = this.lancamentoService.save(lancamento);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
    }
}
