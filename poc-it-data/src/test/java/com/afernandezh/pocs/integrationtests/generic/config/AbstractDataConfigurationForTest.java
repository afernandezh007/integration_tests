package com.afernandezh.pocs.integrationtests.generic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Abstract parent class for the different configuration classes for each DB provider
 */
@Slf4j
@Configuration
public abstract class AbstractDataConfigurationForTest {

    @Value("${datasource.driverClassName}")
    private String driverClassName;

    //admin datasource credentials
    @Value("${datasource.admin.url}")
    private String adminUrl;
    @Value("${datasource.admin.username}")
    private String adminUsername;
    @Value("${datasource.admin.password}")
    private String adminPassword;

    //user datasource credentials
    @Value("${datasource.user.url}")
    private String userUrl;
    @Value("${datasource.user.username}")
    private String userUsername;
    @Value("${datasource.user.password}")
    private String userPassword;

    /**
     * Bean neccessary to read a properties file correctly
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Bean Datasource definition
     * @return
     */
    @Bean
    public DataSource jdbcDataSource() {
        SimpleDriverDataSource dataSource = null;
        try {
            dataSource = new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(adminUrl);
            dataSource.setUsername(adminUsername);
            dataSource.setPassword(adminPassword);
            DatabasePopulatorUtils.execute(adminDatabasePopulator(), dataSource);

            //change to the correct data to use in repos code
            dataSource.setUrl(userUrl);
            dataSource.setUsername(userUsername);
            dataSource.setPassword(userPassword);
            DatabasePopulatorUtils.execute(userDatabasePopulator(), dataSource);

        } catch (Exception e) {
            log.error("Exception thrown while creating the datasource:", e);
        }
        return dataSource;
    }

    /**
     * Method to create the populator for the admin with the scripts needed
     * @return
     */
    private DatabasePopulator adminDatabasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        fillAdminDatabasePopulator(populator);
        return populator;
    }

    /**
     * Abstract method to fill in the concrete configuration class
     * @param populator
     */
    protected abstract void fillAdminDatabasePopulator(ResourceDatabasePopulator populator);

    /**
     * Method to create the populator for the user with the scripts needed
     * @return
     */
    private DatabasePopulator userDatabasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        fillUserDatabasePopulator(populator);
        return populator;
    }

    /**
     * Abstract method to fill in the concrete configuration class
     * @param populator
     */
    protected abstract void fillUserDatabasePopulator(ResourceDatabasePopulator populator);
}
