package user.dao.impl;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import user.dao.UserDao;
import user.model.User;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;

/**
 * created by ewang on 2018/3/20.
 */
@Repository
public class UserDaoImpl implements UserDao, RowMapper<User> {

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
                rs.getString("username"),
                rs.getString("data"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    public User getById(long id) {
        String sql = "select * from user where id = :id";
        return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
    }
}
