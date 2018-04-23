package com.tiago.money.money.service;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.PessoaInativaException;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.LancamentoRepository;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private CategoriaService categoriaService;


    public List<Lancamento> findAll(LancamentoFilter lancamentoFilter) {
        return this.lancamentoRepository.filtrar(lancamentoFilter);
    }

    public Lancamento findById(Long id) {
        Optional<Lancamento> optionLancamento = this.lancamentoRepository.findById(id);

        if (!optionLancamento.isPresent()) {
            throw new NaoEncontradoException("Registro não encontrado");
        }

        return optionLancamento.get();
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
