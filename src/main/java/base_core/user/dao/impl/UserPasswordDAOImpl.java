package base_core.user.dao.impl;

import base_core.user.dao.UserPasswordDAO;
import base_core.user.model.UserPassword;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

/**
 * created by ewang on 2018/3/20.
 */
@Repository
public class UserPasswordDAOImpl implements UserPasswordDAO, RowMapper<UserPassword> {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/user");
        dataSource.setUsername("root");
        dataSource.setPassword("0530");

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public UserPassword mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserPassword(rs.getLong("user_id"),
                rs.getString("password"),
                new Date(rs.getTimestamp("create_time").getTime()),
                rs.getLong("update_time"));
    }

    @Override
    public UserPassword getByUser(long userId) {
        String sql = "select * from user_password where user_id = :userId";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("userId", userId), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

    }

    @Override
    public int insertOrUpdate(long userId, String password, long updateTimeMills) {
        String sql = "insert into user_password(user_id, password, create_time, update_time) " +
                "values(:userId, :password, now(), :updateTime)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("password", password)
                .addValue("updateTime", updateTimeMills);
        return jdbcTemplate.update(sql, params);
    }
}
