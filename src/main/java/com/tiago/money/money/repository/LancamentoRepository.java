package com.tiago.money.money.repository;

import com.tiago.money.money.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository
        extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {}
