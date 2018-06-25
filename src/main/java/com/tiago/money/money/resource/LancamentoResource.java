package com.tiago.money.money.resource;

import com.tiago.money.money.event.RecursoCriadoEvent;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import com.tiago.money.money.bo.LancamentoBO;
import com.tiago.money.money.to.LancamentoEstatisticaPorCategoria;
import com.tiago.money.money.to.LancamentoEstatisticaPorDia;
import com.tiago.money.money.to.ResumoLancamentoTO;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoBO lancamentoBO;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping(value = "/relatorios/por-pessoa")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #aouth2.hasScope('read')")
    public ResponseEntity<byte[]> getRelatoriosLancamentoEstatisticaPorPessoa(
            @RequestParam("data-inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInIcio,
            @RequestParam("data-fim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) throws JRException {
        byte[] report = this.lancamentoBO.getRelatorioLancamentoEstatisticaPorPessoa(dataInIcio, dataFim);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .body(report);
    }

    @GetMapping(value = "/estatisticas/por-categoria")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #oauth2.hasScope('read')")
    public ResponseEntity<List<LancamentoEstatisticaPorCategoria>> buscarEstatisticaPorCategoria() {
        return ResponseEntity.ok(this.lancamentoBO.buscarEstatisticaPorCategoria(LocalDate.now()));
    }

    @GetMapping(value = "/estatisticas/por-dia")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #aouth2.has.Scope('read')")
    public ResponseEntity<List<LancamentoEstatisticaPorDia>> buscarEstatisticaPorDia() {
        return ResponseEntity.ok(this.lancamentoBO.buscarEstatisticaPorDia(LocalDate.now()));
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #oauth2.hasScope('read')")
    public ResponseEntity<Page<Lancamento>> findAll(LancamentoFilter filter,
                                                    org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok().body(this.lancamentoBO.findAll(filter, pageable));
    }

    @GetMapping(params = "resumo")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #oauth2.hasScope('read')")
    public ResponseEntity<Page<ResumoLancamentoTO>> buscarResumo(LancamentoFilter filter, Pageable pageable) {
        return ResponseEntity.ok().body(this.lancamentoBO.buscarResumo(filter, pageable));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') AND #oauth2.hasScope('read')")
    public ResponseEntity<Lancamento> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.lancamentoBO.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_LANCAMENTO') AND #oauth2.hasScope('write')")
    public void save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo  = this.lancamentoBO.save(lancamento);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_LANCAMENTO') AND #oauth2.hasScope('write')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.lancamentoBO.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_ATUALIZAR_LANCAMENTO') AND #oauth2.hasScope('write')")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Lancamento lancamento) {
        this.lancamentoBO.atualizar(id, lancamento);
        return ResponseEntity.noContent().build();
    }
}
