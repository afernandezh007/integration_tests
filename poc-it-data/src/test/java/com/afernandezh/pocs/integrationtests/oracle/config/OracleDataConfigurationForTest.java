package com.afernandezh.pocs.integrationtests.oracle.config;

import com.afernandezh.pocs.integrationtests.generic.config.AbstractDataConfigurationForTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;

/**
 * Configuration class for MySql Provider
 */
@Slf4j
@Configuration
@PropertySource("classpath:db/oracle/datasource.properties")
public class OracleDataConfigurationForTest extends AbstractDataConfigurationForTest {

    //==================================================================================================================
    // Scripts
    //==================================================================================================================
    @Value("classpath:db/oracle/00-test-init.sql")
    private Resource testInitScript;

    @Value("classpath:db/oracle/01-test-ddl.sql")
    private Resource testDDLScript;

    @Value("classpath:db/oracle/02-test-dml.sql")
    private Resource testDMLScript;

    @Override
    protected void fillAdminDatabasePopulator(ResourceDatabasePopulator populator) {
        //NOT USED HERE
    }

    @Override
    protected void fillUserDatabasePopulator(ResourceDatabasePopulator populator) {
        //NOT USED HERE
    }

    /**
     * Bean Datasource definition
     *
     * @return
     */
    @Bean
    @Override
    public DataSource jdbcDataSource() {
        SimpleDriverDataSource dataSource = null;
        try {
            dataSource = new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(adminUrl);
            dataSource.setUsername(adminUsername);
            dataSource.setPassword(adminPassword);
            executeScripts(testInitScript, dataSource, "/");

            //change to the correct data to use in repos code
            dataSource.setUrl(userUrl);
            dataSource.setUsername(userUsername);
            dataSource.setPassword(userPassword);
            executeScripts(testDDLScript, dataSource, "/");
            executeScripts(testDMLScript, dataSource, ";");

        } catch (Exception e) {
            log.error("Exception thrown while creating the datasource:", e);
        }
        return dataSource;
    }

    /**
     * Method to overwrite the executeScripts used in DataBasePopulator to specify the separator used in the script
     *
     * @param script
     * @param dataSource
     * @param separator
     */
    private void executeScripts(Resource script, DataSource dataSource, String separator) {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            try {
                Assert.notNull(connection, "Connection must not be null");

                String encoding = null;
                EncodedResource encodedScript = new EncodedResource(script, encoding);
                ScriptUtils.executeSqlScript(connection, encodedScript, false, false,
                        ScriptUtils.DEFAULT_COMMENT_PREFIX, separator, ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER, ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);

            } finally {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        } catch (Throwable ex) {
            if (ex instanceof ScriptException) {
                throw (ScriptException) ex;
            }
            throw new UncategorizedScriptException("Failed to execute database script", ex);
        }
    }
}
