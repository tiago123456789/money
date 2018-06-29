package com.tiago.money.money.service;

import com.tiago.money.money.facade.NotificadorLancamentoVencidoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgendadorTarefa {

    @Autowired
    private NotificadorLancamentoVencidoFacade notificadorFacade;

    @Scheduled(cron = "* * 12 * * *", zone = "America/Sao_Paulo")
    public void executarTarefas() {
        notificadorFacade.notificar();
    }
}
