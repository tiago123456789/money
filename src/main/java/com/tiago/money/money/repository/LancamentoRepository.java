package com.tiago.money.money.repository;

import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import com.tiago.money.money.to.LancamentoEstatisticaPorCategoria;
import com.tiago.money.money.to.LancamentoEstatisticaPorDia;
import com.tiago.money.money.to.LancamentoEstatisticaPorPessoa;
import com.tiago.money.money.to.ResumoLancamentoTO;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository
        extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate dataAtual);

    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    Page<ResumoLancamentoTO> buscarResumo(LancamentoFilter lancamentoFilter, Pageable pageable);

    List<LancamentoEstatisticaPorPessoa> buscaEstatisticaPorPessoa(LocalDate dataInicial, LocalDate dataFim);
    List<LancamentoEstatisticaPorCategoria> buscarEstatisticaPorCategoria(LocalDate mesReferente);
    List<LancamentoEstatisticaPorDia> buscarEstatisticaPorDia(LocalDate mesReferente);
}
