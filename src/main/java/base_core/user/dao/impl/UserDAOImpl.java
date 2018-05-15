package base_core.user.dao.impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.user.dao.UserDAO;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import base_core.user.model.User;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;

/**
 * created by ewang on 2018/3/20.
 */
@Repository
public class UserDAOImpl implements UserDAO, RowMapper<User> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.User));
    }

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("id"),
                rs.getString("account"),
                rs.getString("data"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    public User getById(long id) {
        String sql = "select * from user where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    public User getByAccount(String account) {
        String sql = "select * from user where account = :account";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("account", account), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    public long insert(String account, String data) {
        String sql = "insert into user(account, data, create_time) values(:account, :data, now())";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("account", account)
                .addValue("data", data);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int updateData(long userId, String newData, String oldData) {
        String sql = "update user set data = :newData where id = :userId and data = :oldData";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId)
                .addValue("newData", newData)
                .addValue("oldData", oldData);
        return jdbcTemplate.update(sql, params);
    }
}
