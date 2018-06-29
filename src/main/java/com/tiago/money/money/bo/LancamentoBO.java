package com.tiago.money.money.bo;

import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.PessoaInativaException;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.model.Pessoa;
import com.tiago.money.money.repository.LancamentoRepository;
import com.tiago.money.money.repository.filter.LancamentoFilter;
import com.tiago.money.money.to.LancamentoEstatisticaPorCategoria;
import com.tiago.money.money.to.LancamentoEstatisticaPorDia;
import com.tiago.money.money.to.LancamentoEstatisticaPorPessoa;
import com.tiago.money.money.to.ResumoLancamentoTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
public class LancamentoBO {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaBO pessoaBO;

    @Autowired
    private CategoriaBO categoriaBO;

    public List<Lancamento> buscarLancamentoVencidosAteDataAtual() {
        return this.lancamentoRepository
                .findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
    }

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
            this.categoriaBO.findOne(lancamento.getCategoria().getId());
            Pessoa pessoa = this.pessoaBO.buscarPorId(lancamento.getPessoa().getId());

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

    public List<LancamentoEstatisticaPorDia> buscarEstatisticaPorDia(LocalDate mesReferente) {
        return this.lancamentoRepository.buscarEstatisticaPorDia(mesReferente);
    }

    public byte[] getRelatorioLancamentoEstatisticaPorPessoa(LocalDate dataInicio, LocalDate dataFim) throws JRException {
        List<LancamentoEstatisticaPorPessoa> dados = this.lancamentoRepository.buscaEstatisticaPorPessoa(dataInicio, dataFim);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("DT_INICIO", java.sql.Date.valueOf(dataInicio));
        parametros.put("DT_FIM", java.sql.Date.valueOf(dataFim));
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(this.getStreamRelatorioPessoa(), parametros, new JRBeanCollectionDataSource(dados));
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private InputStream getStreamRelatorioPessoa() {
        return this.getClass()
                   .getResourceAsStream("/reports/lancamento_por_pessoa.jasper");
    }
}
