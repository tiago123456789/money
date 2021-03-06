package com.tiago.money.money.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String nome;
    private String email;
    private String senha;

    @JoinTable(
            name = "usuario_permissao",
            joinColumns = @JoinColumn(name = "codigo_usuario"),
            inverseJoinColumns = @JoinColumn(name = "codigo_permissao")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permissao> permissao;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Permissao> getPermissao() {
        return permissao;
    }

    public void setPermissao(Set<Permissao> permissao) {
        this.permissao = permissao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return codigo != null ? codigo.equals(usuario.codigo) : usuario.codigo == null;
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}
