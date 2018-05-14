package base_core.session.dao.impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.session.dao.MessageDAO;
import base_core.session.model.Message;
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
public class MessageDAOImpl implements MessageDAO, RowMapper<Message> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.Session));
    }

    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("to_user_id"),
                rs.getString("content"),
                rs.getLong("session_id"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    @Override
    public Message getById(long id) {
        String sql = "select * from message where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Message> findBySession(long sessionId) {
        String sql = "select * from message where session_id = :sessionId";
        return jdbcTemplate.query(sql, Collections.singletonMap("sessionId", sessionId), this);
    }

    @Override
    public Message getLatestBySession(long sessionId) {
        String sql = "select * from message where session_id = :sessionId order by id desc limit 1";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("sessionId", sessionId), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public long insert(long userId, long toUserId, long sessionId, String content) {
        String sql = "insert into message(user_id, to_user_id, session_id, content, create_time) values " +
                "(:userId, :toUserId, :sessionId, :content, now())";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId)
                .addValue("toUserId", toUserId)
                .addValue("sessionId", sessionId)
                .addValue("content", content);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
