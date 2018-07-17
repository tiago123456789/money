package com.tiago.money.money.repository;

import com.tiago.money.money.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    List<Cidade> findByEstadoId(Long estadoId);
}
