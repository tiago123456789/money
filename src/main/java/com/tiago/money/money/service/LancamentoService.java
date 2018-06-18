package com.tiago.money.money.service;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.PessoaInativaException;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.LancamentoRepository;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import com.tiago.money.money.to.LancamentoEstatisticaPorCategoria;
import com.tiago.money.money.to.ResumoLancamentoTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public Page<ResumoLancamentoTO> buscarResumo(LancamentoFilter lancamentoFilter,
                                                 Pageable pageable) {
        return this.lancamentoRepository.buscarResumo(lancamentoFilter, pageable);
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

    public void atualizar(Long id, Lancamento lancamento) {
        Lancamento lancamentoRetornado = this.findById(id);
        BeanUtils.copyProperties(lancamento, lancamentoRetornado, "id");
        this.lancamentoRepository.saveAndFlush(lancamentoRetornado);
    }

    public List<LancamentoEstatisticaPorCategoria> buscarEstatisticaPorCategoria(LocalDate mesReferente) {
        return this.lancamentoRepository.buscarEstatisticaPorCategoria(mesReferente);
    }

}
