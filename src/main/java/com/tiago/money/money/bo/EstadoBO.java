package com.tiago.money.money.bo;

import com.tiago.money.money.model.Estado;
import com.tiago.money.money.repository.EstadoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoBO {

    @Autowired
    private EstadoRespository estadoRespository;

    public List<Estado> getEstados() {
        return this.estadoRespository.findAll();
    }
}
