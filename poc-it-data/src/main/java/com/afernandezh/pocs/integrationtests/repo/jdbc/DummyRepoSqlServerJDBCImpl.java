package com.afernandezh.pocs.integrationtests.repo.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("dummyRepoSqlServerJDBC")
public class DummyRepoSqlServerJDBCImpl extends DummyRepoJDBCImpl{

    @Autowired
    public DummyRepoSqlServerJDBCImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Long getNextId() {
        String sql = "SELECT IDENT_CURRENT (:tableName);";

        Map<String, Object> params = new HashMap<>();
        params.put("tableName", TABLE_NAME);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public void resetId(Long id) {
        String sql = "DBCC CHECKIDENT (:tableName, RESEED, :id);";

        Map<String, Object> params = new HashMap<>();
        params.put("tableName", TABLE_NAME);
        params.put("id", id);

        jdbcTemplate.update(sql, params);
    }

}
