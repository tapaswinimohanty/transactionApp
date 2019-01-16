package com.test.transationApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration

@EntityScan( basePackages = {"com.test.transationApp.entity"} )
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories("com.test.transationApp.repository")
@ComponentScan(basePackages = {"com.test.transationApp.service", "com.test.transationApp.controller", "com.test.transationApp.advice"})

public class TransationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransationAppApplication.class, args);
	}
}
