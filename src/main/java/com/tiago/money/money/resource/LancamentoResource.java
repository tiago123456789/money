package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.lancamento.filter.LancamentoFilter;
import com.tiago.money.money.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #oauth.hasScope('read')")
    public ResponseEntity<Page<Lancamento>> findAll(LancamentoFilter filter, org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok().body(this.lancamentoService.findAll(filter, pageable));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #oauth.hasScope('read')")
    public ResponseEntity<Lancamento> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.lancamentoService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_LANCAMENTO') AND #oauth.hasScope('write')")
    public void save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo  = this.lancamentoService.save(lancamento);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_LANCAMENTO') AND #oauth.hasScope('write')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.lancamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
