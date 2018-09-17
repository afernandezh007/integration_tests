package com.afernandezh.pocs.integrationtests.repo.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("dummyRepoOracleJDBC")
public class DummyRepoOracleJDBCImpl extends DummyRepoJDBCImpl{

    @Autowired
    public DummyRepoOracleJDBCImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        TABLE_NAME = "DUMMYTABLE";
    }

    @Override
    public Long getNextId() {

        String sql = "SELECT DUMMY_SEQ.nextval FROM dual";

        Map<String, Object> params = new HashMap<>();

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public void resetId(Long id) {

        String sql = "BEGIN " +
                "EXECUTE IMMEDIATE 'DROP SEQUENCE DUMMY_SEQ'; " +
                "EXECUTE IMMEDIATE 'CREATE SEQUENCE DUMMY_SEQ START WITH " + id + "'; " +
                "END;";

        Map<String, Object> params = new HashMap<>();

        jdbcTemplate.update(sql, params);
    }
}
