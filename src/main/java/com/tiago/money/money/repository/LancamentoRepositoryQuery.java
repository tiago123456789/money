package com.tiago.money.money.repository;

import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LancamentoRepositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
