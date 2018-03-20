package user.dao.impl;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import user.dao.UserPasswordDAO;
import user.model.UserPassword;

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
        return jdbcTemplate.queryForObject(sql, Collections.singletonMap("userId", userId), this);
    }
}
