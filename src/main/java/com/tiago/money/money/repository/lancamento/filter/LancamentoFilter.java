package com.tiago.money.money.repository.lancamento.filter;

import com.tiago.money.money.config.MoneyConstantes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LancamentoFilter {

    private String descricao;

    @DateTimeFormat(pattern = MoneyConstantes.FORMATO_DATA)
    private LocalDate dataVencimentoDe;

    @DateTimeFormat(pattern = MoneyConstantes.FORMATO_DATA)
    private LocalDate dataVencimentoAte;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimentoDe() {
        return dataVencimentoDe;
    }

    public void setDataVencimentoDe(LocalDate dataVencimentoDe) {
        this.dataVencimentoDe = dataVencimentoDe;
    }

    public LocalDate getDataVencimentoAte() {
        return dataVencimentoAte;
    }

    public void setDataVencimentoAte(LocalDate dataVencimentoAte) {
        this.dataVencimentoAte = dataVencimentoAte;
    }
}
