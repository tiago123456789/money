package com.tiago.money.money.repository;

import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

    List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
