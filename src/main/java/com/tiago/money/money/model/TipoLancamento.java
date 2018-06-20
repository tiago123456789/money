package com.tiago.money.money.model;

public enum TipoLancamento {

    RECEITA("Receita"), DESPESAS("Despesa");

    private String tipo;

    private TipoLancamento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }
}
