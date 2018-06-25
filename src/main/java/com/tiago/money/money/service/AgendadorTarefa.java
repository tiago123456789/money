package com.tiago.money.money.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgendadorTarefa {


    @Scheduled(cron = "* * 12 * * *", zone = "America/Sao_Paulo")
    public void executarTarefas() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Running scheduling task <<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
