package com.afernandezh.pocs.integrationtests.oracle.repo;

import com.afernandezh.pocs.integrationtests.generic.repo.AbstractJDBCTest;
import com.afernandezh.pocs.integrationtests.model.DummyTable;
import com.afernandezh.pocs.integrationtests.oracle.config.OracleDataConfigurationForTest;
import com.afernandezh.pocs.integrationtests.repo.DummyRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class with CRUD for MySql DB
 */
@ContextConfiguration(classes = {OracleDataConfigurationForTest.class})
public class OracleJDBCTest extends AbstractJDBCTest {

    @Autowired
    @Qualifier("dummyRepoOracleJDBC")
    public void setDummyRepo(DummyRepo dummyRepo) {
        this.dummyRepo = dummyRepo;
    }

    @Test
    @Override
    public void testCreate() {
        DummyTable entity = new DummyTable();
        entity.setValue("initial value for created");

        Long nextId = dummyRepo.getNextId();

        dummyRepo.create(entity);
        DummyTable entityReaded = dummyRepo.read(nextId + 1); // the insert always increment the sequence, so we must read current + 1
        assertNotNull("Expected not null value", entityReaded);
        assertNotNull("Expected not null value", entityReaded.getValue());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Override
    public void testDelete() {
        DummyTable entity = new DummyTable();
        entity.setValue("initial value for created to delete");
        Long id = dummyRepo.getNextId();
        dummyRepo.create(entity);
        DummyTable entityReaded = dummyRepo.read(id + 1); // the insert always increment the sequence, so we must read current + 1
        assertNotNull("Expected not null value", entityReaded);
        assertNotNull("Expected not null value", entityReaded.getValue());

        int nDeleted = dummyRepo.delete(id + 1); // the insert always increment the sequence, so we must delete current + 1
        assertEquals("Expected equals value:", 1, nDeleted);
        dummyRepo.read(id); //throws expected EmptyResultDataAccessException
    }
}
