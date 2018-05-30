package com.tiago.money.money.service;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.PessoaInativaException;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.LancamentoRepository;
import com.tiago.money.money.repository.lancamento.filter.LancamentoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private CategoriaService categoriaService;


    public Page<Lancamento> findAll(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return this.lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    public Lancamento findById(Long id) {
        Lancamento lancamento = this.lancamentoRepository.findOne(id);

        if (lancamento == null) {
            throw new NaoEncontradoException("Registro não encontrado");
        }

        return lancamento;
    }

    public Lancamento save(Lancamento lancamento) {
        try {
            this.categoriaService.findOne(lancamento.getCategoria().getId());
            Pessoa pessoa = this.pessoaService.buscarPorId(lancamento.getPessoa().getId());

            if (!pessoa.getAtivo()) {
                throw new PessoaInativaException("Pessoa está inativa, por isso não pode ser associada a um lançamento!");
            }

            return this.lancamentoRepository.save(lancamento);
        } catch (NaoEncontradoException e) {
            throw new NaoEncontradoException(e);
        }
    }

    public void delete(Long id) {
        Lancamento lancamento = this.findById(id);
        this.lancamentoRepository.delete(lancamento);
    }
}
