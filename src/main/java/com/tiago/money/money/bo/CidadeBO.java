package com.tiago.money.money.bo;

import com.tiago.money.money.model.Cidade;
import com.tiago.money.money.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeBO {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> searchCitiesInStateSpecified(Long idEstado) {
        return this.cidadeRepository.findByEstadoId(idEstado);
    }
}
