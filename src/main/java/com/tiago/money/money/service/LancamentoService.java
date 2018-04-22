package com.tiago.money.money.service;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;


    public List<Lancamento> findAll() {
        return this.lancamentoRepository.findAll();
    }

    public Lancamento findById(Long id) {
        Optional<Lancamento> optionLancamento = this.lancamentoRepository.findById(id);

        if (!optionLancamento.isPresent()) {
            throw new NaoEncontradoException("Registro não encontrado");
        }

        return optionLancamento.get();
    }

    public Lancamento save(Lancamento lancamento) {
        return this.lancamentoRepository.save(lancamento);
    }
}
