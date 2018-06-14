package com.tiago.money.money.repository.impl;


import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Pessoa> filtrar(PessoaFilter filtro, Pageable paginacao) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> query = builder.createQuery(Pessoa.class);
        Root<Pessoa> pessoaRoot = query.from(Pessoa.class);

        query.where(this.getFiltersQuery(filtro, builder, pessoaRoot));
        TypedQuery<Pessoa> pessoaTypedQuery = this.entityManager.createQuery(query);
        this.applyPagination(pessoaTypedQuery, paginacao);

        return new PageImpl<>(pessoaTypedQuery.getResultList(), paginacao, this.getQuantityRegister(filtro));
    }

    private Long getQuantityRegister(PessoaFilter filtro) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Pessoa> pessoaRoot = query.from(Pessoa.class);

        query.select(builder.count(pessoaRoot.get("id")));
        query.where(this.getFiltersQuery(filtro, builder, pessoaRoot));
        return this.entityManager.createQuery(query).getSingleResult();
    }

    private Predicate[] getFiltersQuery(PessoaFilter filtro, CriteriaBuilder builder, Root<Pessoa> pessoaRoot) {
        List<Predicate> filters = new ArrayList<>();

        if (filtro.getNome() != null) {
            filters.add(builder.like(builder.upper(pessoaRoot.get("nome")), filtro.getNome().toUpperCase()));
        }

        if (filtro.getAtivo() != null) {
            filters.add(builder.equal(pessoaRoot.get("ativo"), filtro.getAtivo()));
        }

        if (filtro.getNomeBairro() != null) {
            filters.add(builder.like(builder.upper(pessoaRoot.get("bairro")), filtro.getNomeBairro()));
        }

        return filters.toArray(new Predicate[]{});
    }

    private void applyPagination(Query query, Pageable pagination) {
        int pageCurrent = pagination.getPageNumber() == 0 ? 1 : pagination.getPageNumber();
        int pageSize = pagination.getPageSize() == 0 ? 10 : pagination.getPageSize();
        int positionInitial = (pageCurrent - 1) * pageSize;
        query.setFirstResult(positionInitial);
        query.setMaxResults(pagination.getPageSize());
    }
}
