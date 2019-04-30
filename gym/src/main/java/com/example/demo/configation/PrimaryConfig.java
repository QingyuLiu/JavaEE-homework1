package com.example.demo.configation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.JpaTransactionManager;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryPrimary",
        transactionManagerRef="transactionManagerPrimary",
        basePackages= { "com.example.demo.repository" }) //设置Repository所在位置

public class PrimaryConfig {

	 	@Autowired
	    private HibernateProperties hibernatePropertities;
	    @Resource 
	    private JpaProperties jpaProperties;
	    @Autowired 
	    @Qualifier("primaryDataSource")
	    private DataSource primaryDataSource;

	    @Primary
	    @Bean(name = "entityManagerPrimary")
	    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
	        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
	    }
	    
	   

	    @Primary
	    @Bean(name = "entityManagerFactoryPrimary")
	    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary (EntityManagerFactoryBuilder builder) {
	        Map<String,Object> properties=hibernatePropertities.determineHibernateProperties(jpaProperties.getProperties(),new HibernateSettings());
	    	return builder
	                .dataSource(primaryDataSource)
	                .properties(properties)
	                .packages("com.example.demo.entity") //设置实体类所在位置
	                .persistenceUnit("primaryPersistenceUnit")
	                .build();
	    	      
	    }

	   



	    @Primary
	    @Bean(name = "transactionManagerPrimary")
	    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
	        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
	    }
	
	
}
