package com.tiago.money.money.service;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.NegocioException;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> buscarTodas() {
        return this.pessoaRepository.findAll();
    }

    public Pessoa salvar(Pessoa pessoa) {
        if (this.verificarSeNomeEstaEmUso(pessoa.getNome())) {
            throw new NegocioException("Nome já está sendo usado.");
        }

        return this.pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        if (this.verificaSeNaoExistePessoa(id)) {
            throw new NaoEncontradoException("Pessoa não existe.");
        }

        this.pessoaRepository.deleteById(id);
    }

    public void atualizar(Long id, Pessoa pessoa) {
        Optional<Pessoa> optionalPessoaRetornada = this.pessoaRepository.findById(id);
        if (!optionalPessoaRetornada.isPresent()) {
            throw new NaoEncontradoException("Pessoa não existe.");
        }

        Pessoa pessoaRetornada = optionalPessoaRetornada.get();
        BeanUtils.copyProperties(pessoa, pessoaRetornada, "id");
        this.pessoaRepository.save(pessoaRetornada);
    }

    private boolean verificaSeNaoExistePessoa(Long id) {
        return !this.pessoaRepository.findById(id).isPresent();
    }

    private boolean verificarSeNomeEstaEmUso(String nome) {
        return this.pessoaRepository.findByNome(nome) != null;
    }
}
