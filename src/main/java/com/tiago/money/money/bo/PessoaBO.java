package com.tiago.money.money.bo;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.NegocioException;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.PessoaRepository;
import com.tiago.money.money.repository.filter.PessoaFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaBO {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Page<Pessoa> buscarTodas(PessoaFilter filter, Pageable pagination) {
        return this.pessoaRepository.filtrar(filter, pagination);
    }

    public Pessoa salvar(Pessoa pessoa) {
        if (this.verificarSeNomeEstaEmUso(pessoa.getNome())) {
            throw new NegocioException("Nome já está sendo usado.");
        }

        return this.pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        Pessoa pessoaRetornada = this.buscarPorId(id);
        this.pessoaRepository.delete(pessoaRetornada);
    }

    public void atualizar(Long id, Pessoa pessoa) {
        Pessoa pessoaRetornada = this.buscarPorId(id);
        BeanUtils.copyProperties(pessoa, pessoaRetornada, "id");
        this.pessoaRepository.save(pessoaRetornada);
    }

    public void atualizarPropiedadeAtivo(Long id, Boolean ativo) {
        Pessoa pessoaRetornada = this.buscarPorId(id);
        pessoaRetornada.setAtivo(ativo);
        this.pessoaRepository.save(pessoaRetornada);
    }

    public Pessoa buscarPorId(Long id) {
        Pessoa pessoaRetornada = this.pessoaRepository.findOne(id);
        if (pessoaRetornada == null) {
            throw new NaoEncontradoException("Pessoa não existe.");
        }

        return pessoaRetornada;
    }

    private boolean verificarSeNomeEstaEmUso(String nome) {
        return this.pessoaRepository.findByNome(nome) != null;
    }
}
