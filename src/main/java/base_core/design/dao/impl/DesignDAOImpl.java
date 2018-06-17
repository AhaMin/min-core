package base_core.design.dao.impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.design.dao.DesignDAO;
import base_core.design.model.Design;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;

/**
 * created by ewang on 2018/6/14.
 */
@Repository
public class DesignDAOImpl implements DesignDAO, RowMapper<Design> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Design mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Design(rs.getLong("id"),
                rs.getLong("owner_id"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.Design));
    }

    @Override
    public long insert(long ownerId) {
        String sql = "insert into design(owner_id,create_time) values(:ownerId,now())";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("ownerId", ownerId);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Design getById(long id) {
        String sql = "select * from design where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


}
