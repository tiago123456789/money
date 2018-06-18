package com.tiago.money.money.to;

import com.tiago.money.money.model.Categoria;

import java.math.BigDecimal;

public class LancamentoEstatisticaPorCategoria {

    private Categoria categoria;

    private BigDecimal total;

    public LancamentoEstatisticaPorCategoria(Categoria categoria, BigDecimal total) {
        this.categoria = categoria;
        this.total = total;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
