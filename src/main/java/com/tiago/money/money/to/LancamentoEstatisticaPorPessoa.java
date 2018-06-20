package com.tiago.money.money.to;

import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.model.TipoLancamento;

import java.math.BigDecimal;

public class LancamentoEstatisticaPorPessoa {

    private TipoLancamento tipoLancamento;
    private Pessoa pessoa;
    private BigDecimal total;

    public LancamentoEstatisticaPorPessoa(TipoLancamento tipoLancamento, Pessoa pessoa, BigDecimal total) {
        this.tipoLancamento = tipoLancamento;
        this.pessoa = pessoa;
        this.total = total;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
