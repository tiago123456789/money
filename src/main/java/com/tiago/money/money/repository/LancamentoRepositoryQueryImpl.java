package com.tiago.money.money.repository;

import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.filter.LancamentoFilter;
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

public class LancamentoRepositoryQueryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> query = criteriaBuilder.createQuery(Lancamento.class);
        Root<Lancamento> root = query.from(Lancamento.class);
        query
                .where(this.getRestricoesConsulta(criteriaBuilder, root, lancamentoFilter)
                .toArray(new Predicate[]{}));

        return this.manager.createQuery(query).getResultList();
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
