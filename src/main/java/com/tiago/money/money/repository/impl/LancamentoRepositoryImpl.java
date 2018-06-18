package com.tiago.money.money.repository.impl;

import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import com.tiago.money.money.to.LancamentoEstatisticaPorCategoria;
import com.tiago.money.money.to.ResumoLancamentoTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public LancamentoRepositoryImpl() {}

    public List<LancamentoEstatisticaPorCategoria> buscarEstatisticaPorCategoria(LocalDate mesReferente) {
        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoEstatisticaPorCategoria> query = builder.createQuery(LancamentoEstatisticaPorCategoria.class);
        Root<Lancamento> rootLancamento = query.from(Lancamento.class);

        query.select(builder.construct(
                LancamentoEstatisticaPorCategoria.class, rootLancamento.get("categoria"),
                builder.sum(rootLancamento.get("valor")))
        );

        LocalDate dataComPrimeiroDiaMes = mesReferente.withDayOfMonth(1);
        LocalDate dataComUltimoDiaMes = mesReferente.withDayOfMonth(mesReferente.lengthOfMonth());

        query.where(
                builder.greaterThanOrEqualTo(rootLancamento.get("dataVencimento"), dataComPrimeiroDiaMes),
                builder.lessThanOrEqualTo(rootLancamento.get("dataVencimento"), dataComUltimoDiaMes)
        );

        query.groupBy(rootLancamento.get("categoria"));

        return this.manager.createQuery(query).getResultList();
    }

    public Page<ResumoLancamentoTO> buscarResumo(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<ResumoLancamentoTO> query  = builder.createQuery(ResumoLancamentoTO.class);
        Root<Lancamento> root = query.from(Lancamento.class);

        query.select(builder.construct(ResumoLancamentoTO.class,
                root.get("id"), root.get("descricao"), root.get("dataVencimento"),
                root.get("dataPagamento"), root.get("valor"), root.get("tipoLancamento"),
                root.get("categoria").get("name"),
                root.get("pessoa").get("nome")));

        query
                .where(this.getRestricoesConsulta(builder, root, lancamentoFilter)
                        .toArray(new Predicate[]{}));

        TypedQuery<ResumoLancamentoTO> lancamentoTypedQuery = this.manager.createQuery(query);
        this.definirPaginacaoNaConsulta(lancamentoTypedQuery, pageable);
        return new PageImpl<ResumoLancamentoTO>(lancamentoTypedQuery.getResultList(), pageable, this.getQuantidadeLancamentos(lancamentoFilter));
    }

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

    private void definirPaginacaoNaConsulta(TypedQuery<?> query, org.springframework.data.domain.Pageable pageable) {
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
