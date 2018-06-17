package base_core.user.dao.impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.user.dao.UserAddressDAO;
import base_core.user.model.UserAddress;
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
import java.util.List;

/**
 * created by ewang on 2018/6/14.
 */
@Repository
public class UserAddressDAOImpl implements UserAddressDAO, RowMapper<UserAddress> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.User));
    }

    @Override
    public UserAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserAddress(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("data"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    @Override
    public UserAddress getById(long addressId) {
        String sql = "select * from user_address where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", addressId), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public long insert(long userId, String data) {
        String sql = "insert into user_address(user_id,data,create_time) values(:userId,:data,now())";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId)
                .addValue("data", data);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<UserAddress> findByUser(long userId) {
        String sql = "select * from user_address where user_id = :userId";
        return jdbcTemplate.query(sql, Collections.singletonMap("userId", userId), this);
    }
}
