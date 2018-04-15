package com.tiago.money.money.service;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.NegocioException;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa salvar(Pessoa pessoa) {
        if (this.verificarSeNomeEstaEmUso(pessoa.getNome())) {
            throw new NegocioException("Nome já está sendo usado.");
        }

        return this.pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        if (this.verificaSeNaoExistePessoa(id)) {
            throw new NaoEncontradoException("Registro não existe.");
        }

        this.pessoaRepository.deleteById(id);
    }

    private boolean verificaSeNaoExistePessoa(Long id) {
        return !this.pessoaRepository.findById(id).isPresent();
    }

    private boolean verificarSeNomeEstaEmUso(String nome) {
        return this.pessoaRepository.findByNome(nome) != null;
    }
}
