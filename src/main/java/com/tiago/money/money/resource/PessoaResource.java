package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.service.PessoaService;
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
@RequestMapping(value = "pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PESSOA') AND #oauth2.hasScope('read')")
    public ResponseEntity<List<Pessoa>> buscarTodas() {
        return ResponseEntity.ok().body(this.pessoaService.buscarTodas());
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CADASTRO') AND #oauth2.hasScope('read') ")
    public ResponseEntity<?> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = this.pessoaService.salvar(pessoa);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_PESSOA') AND #oauth2.hasScope('write')")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        this.pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_ATUALIZAR_PESSOA') AND #oauth2.hasScope('write')")
    public ResponseEntity<Void> atualizar(@PathVariable Long id,  @Valid @RequestBody Pessoa pessoa) {
        this.pessoaService.atualizar(id, pessoa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/ativo")
    @PreAuthorize(value = "hasAuthority('ROLE_ATIVAR_INATIVAR_PESSOA') AND #oauth2.hasScope('write')")
    public ResponseEntity<Void> atualizarPropiedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
        this.pessoaService.atualizarPropiedadeAtivo(id, ativo);
        return ResponseEntity.noContent().build();
    }
}
