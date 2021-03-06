package com.example.demo.configation;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class DataSourcesCinfig {

	 @Bean(name = "primaryDataSource")
	 @Primary
	 @Qualifier("primaryDataSource")    
	 @ConfigurationProperties(prefix = "spring.datasource.primary")    
	  public DataSource primaryDataSource() {
	        return DataSourceBuilder.create().build();
	    }
  
	 
	 @Bean(name = "secondaryDataSource")   
	 @Qualifier("secondaryDataSource") 
	 @ConfigurationProperties(prefix = "spring.datasource.secondary")
	 public DataSource secondaryDataSource() {
	        return DataSourceBuilder.create().build();
	    }


	
}
