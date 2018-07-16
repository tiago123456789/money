package com.tiago.money.money.model;

import javax.persistence.*;

@Entity
@Table(name = "cidade" )
public class Cidade {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "codigo_estado")
    private Estado estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
