package com.afernandezh.pocs.integrationtests.generic.repo;

import com.afernandezh.pocs.integrationtests.config.DataConfiguration;
import com.afernandezh.pocs.integrationtests.model.DummyTable;
import com.afernandezh.pocs.integrationtests.repo.DummyRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfiguration.class)
@Transactional
public abstract class AbstractJDBCTest {

    protected DummyRepo dummyRepo;

    private Long initialId;

    /**
     * This method stores the initialId autoincremented to restore after each
     * transaction because this data in DB is changed even with rollback,
     * it is out of the transaction
     */
    @BeforeTransaction
    public void beforeMethod() {
        initialId = dummyRepo.getNextId();
        log.info("InitialId = " + initialId);
    }

    /**
     * This method restores the initialId autoincremented after each transaction
     */
    @AfterTransaction
    public void afterMethod() {
        dummyRepo.resetId(initialId);
        log.info("reset InitialId = " + initialId);
    }

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
