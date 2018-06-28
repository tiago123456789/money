package com.tiago.money.money.config;

import com.tiago.money.money.config.property.MoneyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Autowired
    private MoneyProperty moneyProperty;

    @Bean
    public JavaMailSender getJavaMailSender() {
        Properties configuracaoEnvioEmail = new Properties();
        configuracaoEnvioEmail.put("mail.transport.protocol", "smtp");
        configuracaoEnvioEmail.put("mail.smtp.auth", true);
        configuracaoEnvioEmail.put("mail.smtp.starttls.enable", true);
        configuracaoEnvioEmail.put("mail.smtp.connectiontimeout", 10000);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(configuracaoEnvioEmail);
        javaMailSender.setHost(moneyProperty.getEmail().getHost());
        javaMailSender.setPort(moneyProperty.getEmail().getPort());
        javaMailSender.setUsername(moneyProperty.getEmail().getUsername());
        javaMailSender.setPassword(moneyProperty.getEmail().getPassword());

        return javaMailSender;
    }
}
