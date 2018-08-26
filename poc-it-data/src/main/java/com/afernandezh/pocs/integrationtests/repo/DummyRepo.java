package com.afernandezh.pocs.integrationtests.repo;

import com.afernandezh.pocs.integrationtests.model.DummyTable;

import java.util.Set;

public interface DummyRepo {

    Long getNextId();

    Set<DummyTable> findAll();

    void create(DummyTable entity);

    DummyTable read(Long id);

    int update(Long id, String value);

    int delete(Long id);
}
