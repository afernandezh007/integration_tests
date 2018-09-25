package com.afernandezh.pocs.integrationtests.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.afernandezh.pocs.integrationtests.repo.*")
public class DataConfiguration {

    //1. dataSource (defined in launcher or test)
    @Autowired
    private DataSource dataSource;

    //2. jdbctemplate
    @Bean
    public NamedParameterJdbcTemplate myJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    //3. TransactionManager
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
