package com.tiago.money.money.repository;

import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.model.Usuario;
import com.tiago.money.money.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByNome(String nome);

    Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable paginacao);

    List<Usuario> findByPermissaoDescricao(String descricao);
}
