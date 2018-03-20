package base_core.user.dao.impl;

import base_core.user.dao.UserDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/user");
        dataSource.setUsername("root");
        dataSource.setPassword("0530");

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("id"),
                rs.getString("account"),
                rs.getString("data"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    public User getById(long id) {
        String sql = "select * from user where id = :id";
        return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
    }

    @Override
    public User getByAccount(String account) {
        String sql = "select * from user where account = account";
        return jdbcTemplate.queryForObject(sql, Collections.singletonMap("account", account), this);
    }

    @Override
    public long insert(String account, String data) {
        String sql = "insert into user(account, data, create_time) values(:account, :data, now())";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("account", account)
                .addValue("data", data);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
