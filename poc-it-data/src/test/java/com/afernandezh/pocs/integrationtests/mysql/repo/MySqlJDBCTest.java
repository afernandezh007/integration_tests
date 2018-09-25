package com.afernandezh.pocs.integrationtests.mysql.repo;

import com.afernandezh.pocs.integrationtests.generic.repo.AbstractJDBCTest;
import com.afernandezh.pocs.integrationtests.mysql.config.MySqlDataConfigurationForTest;
import com.afernandezh.pocs.integrationtests.repo.DummyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test class with CRUD for MySql DB
 */
@ContextConfiguration(classes = {MySqlDataConfigurationForTest.class})
public class MySqlJDBCTest extends AbstractJDBCTest {

    @Autowired
    @Qualifier("dummyRepoMySqlJDBC")
    public void setDummyRepo(DummyRepo dummyRepo) {
        this.dummyRepo = dummyRepo;
    }
}
