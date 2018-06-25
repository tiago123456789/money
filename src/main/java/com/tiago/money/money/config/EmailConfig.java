package com.tiago.money.money.config;

import com.tiago.money.money.config.profile.MoneyProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Autowired
    private MoneyProfile moneyProfile;

    @Bean
    public JavaMailSender getJavaMailSender() {
        Properties configuracaoEnvioEmail = new Properties();
        configuracaoEnvioEmail.put("mail.transport.protocol", "stmp");
        configuracaoEnvioEmail.put("mail.smtp.auth", true);
        configuracaoEnvioEmail.put("mail.stmp.stattls.enable", true);
        configuracaoEnvioEmail.put("mail.stmp.connectiontimeout", 100000);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(configuracaoEnvioEmail);
        javaMailSender.setHost(moneyProfile.getEmail().getHost());
        javaMailSender.setPort(moneyProfile.getEmail().getPort());
        javaMailSender.setUsername(moneyProfile.getEmail().getUsername());
        javaMailSender.setPassword(moneyProfile.getEmail().getPassword());
    }
}
