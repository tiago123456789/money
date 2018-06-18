package com.tiago.money.money.to;

import com.tiago.money.money.model.TipoLancamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoEstatisticaPorDia {

    private TipoLancamento tipoLancamento;

    private LocalDate dia;

    private BigDecimal total;

    public LancamentoEstatisticaPorDia() {}

    public LancamentoEstatisticaPorDia(TipoLancamento tipoLancamento, LocalDate dia, BigDecimal total) {
        this.tipoLancamento = tipoLancamento;
        this.dia = dia;
        this.total = total;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
