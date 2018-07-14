package com.tiago.money.money;

import com.tiago.money.money.config.property.MoneyProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(value = MoneyProperty.class)
public class MoneyApplication {

	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(MoneyApplication.class, args);
	}

	public static <T> T getBean(Class<T> objeto) {
		return APPLICATION_CONTEXT.getBean(objeto);
	}
}
