package com.afernandezh.pocs.integrationtests.repo.jdbc;

import com.afernandezh.pocs.integrationtests.model.DummyTable;
import com.afernandezh.pocs.integrationtests.repo.DummyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository("dummyRepoJDBC")
public class DummyRepoJDBCImpl implements DummyRepo {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private DummyTableRowMapper rowMapper = new DummyTableRowMapper();

    private class DummyTableRowMapper implements RowMapper<DummyTable> {

        @Override
        public DummyTable mapRow(ResultSet rs, int rowNum) throws SQLException {

            DummyTable entity = new DummyTable();
            entity.setId(rs.getLong("id"));
            entity.setValue(rs.getString("value"));
            return entity;
        }
    }

    @Autowired
    public DummyRepoJDBCImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long getNextId() {
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = :tableName;";

        Map<String, Object> params = new HashMap<>();
        params.put("tableName", "dummytable");

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public Set<DummyTable> findAll() {
        String sql = "SELECT * FROM dummytable;";

        return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
    }

    @Override
    public void create(DummyTable entity) {
        String sql = "INSERT INTO dummytable(value) VALUES(:value)";

        Map<String, Object> params = new HashMap<>();
        params.put("value", entity.getValue());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public DummyTable read(Long id) {
        String sql = "SELECT * FROM dummytable WHERE id=:id;";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbcTemplate.queryForObject(sql, params, rowMapper);
    }

    @Override
    public int update(Long id, String value) {
        String sql = "UPDATE dummytable SET value=:value WHERE id=:id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("value", value);

        return jdbcTemplate.update(sql, params);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM dummytable WHERE id=:id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbcTemplate.update(sql, params);
    }
}
