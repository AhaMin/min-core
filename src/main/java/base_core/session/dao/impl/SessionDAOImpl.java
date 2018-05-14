package base_core.session.dao.impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.session.dao.SessionDAO;
import base_core.session.model.Session;
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
 * created by ewang on 2018/4/18.
 */
@Repository
public class SessionDAOImpl implements SessionDAO, RowMapper<Session> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.Session));
    }

    public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Session(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("to_user_id"),
                new Date(rs.getTimestamp("create_time").getTime()),
                new Date(rs.getTimestamp("update_time").getTime()),
                rs.getInt("unread"));
    }

    @Override
    public Session getById(long id) {
        String sql = "select * from session where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException r) {
            return null;
        }
    }

    @Override
    public Session getByUserAndToUser(long userId, long toUserId) {
        String sql = "select * from session where user_id = :userId and to_user_id = :toUserId";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId);
        parameterSource.addValue("toUserId", toUserId);
        try {
            return jdbcTemplate.queryForObject(sql, parameterSource, this);
        } catch (IncorrectResultSizeDataAccessException r) {
            return null;
        }
    }

    public long insert(long userId, long toUserId) {
        String sql = "insert into session (user_id, to_user_id, create_time, update_time, unread) values (:userId, :toUserId, now(), now(), 0)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId);
        parameterSource.addValue("toUserId", toUserId);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int updateUnread(long sessionId, int oldData, int newData) {
        String sql = "update session set unread = :newData where id = :sessionId and unread = :oldData";
        MapSqlParameterSource params = new MapSqlParameterSource("newData", newData)
                .addValue("oldData", oldData)
                .addValue("sessionId", sessionId);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public int updateTime(long sessionId, Date updateTime) {
        String sql = "update session set update_time = :updateTime where id = :sessionId";
        MapSqlParameterSource params = new MapSqlParameterSource("updateTime", updateTime)
                .addValue("sessionId", sessionId);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Session> findLatestSessionByUser(long userId, int limit) {
        String sql = "select * from session where user_id = :userId order by create_time desc limit :limit";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId)
                .addValue("limit", limit);
        return jdbcTemplate.query(sql, params, this);
    }
}
