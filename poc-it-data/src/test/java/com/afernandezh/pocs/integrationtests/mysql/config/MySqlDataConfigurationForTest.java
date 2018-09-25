package com.afernandezh.pocs.integrationtests.mysql.config;

import com.afernandezh.pocs.integrationtests.generic.config.AbstractDataConfigurationForTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Configuration class for MySql Provider
 */
@Configuration
@PropertySource("classpath:db/mysql/datasource.properties")
public class MySqlDataConfigurationForTest extends AbstractDataConfigurationForTest {

    //==================================================================================================================
    // Scripts
    //==================================================================================================================
    @Value("classpath:db/mysql/00-test-init.sql")
    private Resource testInitScript;

    @Value("classpath:db/mysql/01-test-ddl.sql")
    private Resource testDDLScript;

    @Value("classpath:db/mysql/02-test-dml.sql")
    private Resource testDMLScript;

    @Override
    protected void fillAdminDatabasePopulator(ResourceDatabasePopulator populator) {
        populator.addScript(testInitScript);
    }

    @Override
    protected void fillUserDatabasePopulator(ResourceDatabasePopulator populator) {
        populator.addScript(testDDLScript);
        populator.addScript(testDMLScript);
    }
}
