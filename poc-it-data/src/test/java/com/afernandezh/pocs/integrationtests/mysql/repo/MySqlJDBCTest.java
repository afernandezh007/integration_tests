package com.afernandezh.pocs.integrationtests.mysql.repo;

import com.afernandezh.pocs.integrationtests.config.DataConfiguration;
import com.afernandezh.pocs.integrationtests.model.DummyTable;
import com.afernandezh.pocs.integrationtests.mysql.config.MySqlDataConfigurationForTest;
import com.afernandezh.pocs.integrationtests.repo.DummyRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class with CRUD for MySql DB
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfiguration.class, MySqlDataConfigurationForTest.class})
@Transactional
public class MySqlJDBCTest {

    @Autowired
    @Qualifier("dummyRepoJDBC")
    private DummyRepo dummyRepo;

    @Test
    public void testGetNextId() {
        Long nextId = dummyRepo.getNextId();
        assertNotNull("Expected not null value", nextId);
    }

    @Test
    public void testFindAll() {
        Set<DummyTable> all = dummyRepo.findAll();
        assertNotNull("Expected not null collection", all);
    }

    @Test
    public void testCreate() {
        DummyTable entity = new DummyTable();
        entity.setValue("initial value for created");

        Long nextId = dummyRepo.getNextId();

        dummyRepo.create(entity);
        DummyTable entityReaded = dummyRepo.read(nextId);
        assertNotNull("Expected not null value", entityReaded);
        assertNotNull("Expected not null value", entityReaded.getValue());
    }

    @Test
    public void testRead() {
        DummyTable entity = dummyRepo.read(1L);
        assertNotNull("Expected not null value", entity);
    }

    @Test
    public void testUpdate() {

        Long id = 1L;
        String value = "modified value";

        int nUpdated = dummyRepo.update(id, value);

        DummyTable entity = dummyRepo.read(id);

        assertEquals("Expected equals value:", 1, nUpdated);
        assertEquals("Expected equals value:", value, entity.getValue());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDelete() {
        DummyTable entity = new DummyTable();
        entity.setValue("initial value for created to delete");
        Long id = dummyRepo.getNextId();
        dummyRepo.create(entity);
        DummyTable entityReaded = dummyRepo.read(id);
        assertNotNull("Expected not null value", entityReaded);
        assertNotNull("Expected not null value", entityReaded.getValue());

        int nDeleted = dummyRepo.delete(id);
        assertEquals("Expected equals value:", 1, nDeleted);
        dummyRepo.read(id); //throws expected EmptyResultDataAccessException
    }

}
