package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@EnableCaching

@SpringBootApplication
@Configuration

@EntityScan
@EnableJpaRepositories(basePackages = "webroot.webserv",
entityManagerFactoryRef = "entityManagerFactory",
transactionManagerRef = "transactionManager")

@EnableTransactionManagement
@EnableAutoConfiguration
@MapperScan("com.example.demo.mapper")
public class GymApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymApplication.class, args);
	}

}


