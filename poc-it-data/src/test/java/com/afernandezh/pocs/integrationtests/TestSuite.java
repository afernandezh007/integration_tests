package com.afernandezh.pocs.integrationtests;

import com.afernandezh.pocs.integrationtests.mysql.repo.MySqlJDBCTest;
import com.afernandezh.pocs.integrationtests.oracle.repo.OracleJDBCTest;
import com.afernandezh.pocs.integrationtests.sqlserver.repo.SqlServerJDBCTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite to execute all tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MySqlJDBCTest.class,
        SqlServerJDBCTest.class,
        OracleJDBCTest.class
})
public class TestSuite {
}
