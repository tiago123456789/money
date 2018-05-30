package com.tiago.money.money.repository;

import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.lancamento.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository
        extends JpaRepository<Lancamento, Long> {

    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
