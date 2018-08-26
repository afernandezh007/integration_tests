package com.afernandezh.pocs.integrationtests.repo.jdbc;

import com.afernandezh.pocs.integrationtests.repo.DummyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("dummyRepoMySqlJDBC")
public class DummyRepoMySqlJDBCImpl extends DummyRepoJDBCImpl implements DummyRepo {

    @Autowired
    public DummyRepoMySqlJDBCImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Long getNextId() {
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = :tableName;";

        Map<String, Object> params = new HashMap<>();
        params.put("tableName", "dummytable");//doesn't works with schema name like prefix

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public void resetId(Long id) {
        String sql = "ALTER TABLE " + TABLE_NAME + " AUTO_INCREMENT = " + id + ";";

        Map<String, Object> params = new HashMap<>();

        jdbcTemplate.update(sql, params);
    }
}
