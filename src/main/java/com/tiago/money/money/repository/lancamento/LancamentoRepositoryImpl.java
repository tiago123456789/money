package com.tiago.money.money.repository.lancamento;

import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.lancamento.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public LancamentoRepositoryImpl() {}

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, org.springframework.data.domain.Pageable pageable) {
        CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> query = criteriaBuilder.createQuery(Lancamento.class);
        Root<Lancamento> root = query.from(Lancamento.class);
        query
                .where(this.getRestricoesConsulta(criteriaBuilder, root, lancamentoFilter)
                .toArray(new Predicate[]{}));

        TypedQuery<Lancamento> lancamentoTypedQuery = this.manager.createQuery(query);
        this.definirPaginacaoNaConsulta(lancamentoTypedQuery, pageable);
        return new PageImpl<>(lancamentoTypedQuery.getResultList(), pageable, this.getQuantidadeLancamentos(lancamentoFilter));
    }


    private Long getQuantidadeLancamentos(LancamentoFilter filter) {
        CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Lancamento> root = query.from(Lancamento.class);

        query.where(this.getRestricoesConsulta(criteriaBuilder, root, filter).toArray(new Predicate[]{}));
        query.select(criteriaBuilder.count(root));
        return this.manager.createQuery(query).getSingleResult();
    }

    private void definirPaginacaoNaConsulta(TypedQuery<Lancamento> query, org.springframework.data.domain.Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private List<Predicate> getRestricoesConsulta(
            CriteriaBuilder builder, Root<Lancamento> root, LancamentoFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(filter.getDescricao())) {
            predicates.add(builder.like(
                    builder.lower(root.get("descricao")),
                    "%" + filter.getDescricao().toLowerCase() + "%")
            );
        }

        if (filter.getDataVencimentoDe() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoDe()));
        }

        if (filter.getDataVencimentoAte() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoAte()));
        }

        return predicates;
    }
}
