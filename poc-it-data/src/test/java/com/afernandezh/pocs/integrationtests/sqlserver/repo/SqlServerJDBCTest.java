package com.afernandezh.pocs.integrationtests.sqlserver.repo;

import com.afernandezh.pocs.integrationtests.generic.repo.AbstractJDBCTest;
import com.afernandezh.pocs.integrationtests.repo.DummyRepo;
import com.afernandezh.pocs.integrationtests.sqlserver.config.SqlServerDataConfigurationForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test class with CRUD for SqlServer DB
 */
@ContextConfiguration(classes = {SqlServerDataConfigurationForTest.class})
public class SqlServerJDBCTest extends AbstractJDBCTest {

    @Autowired
    @Qualifier("dummyRepoSqlServerJDBC")
    public void setDummyRepo(DummyRepo dummyRepo) {
        this.dummyRepo = dummyRepo;
    }
}
