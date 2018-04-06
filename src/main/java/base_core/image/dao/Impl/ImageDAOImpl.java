package base_core.image.dao.Impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.image.dao.ImageDAO;
import base_core.image.model.Image;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

/**
 * created by ewang on 2018/3/23.
 */
@Repository
public class ImageDAOImpl implements ImageDAO, RowMapper<Image> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.Image));
    }

    @Override
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Image(rs.getLong("id"),
                rs.getString("data"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    @Override
    public Image getById(long id) {
        String sql = "select * from image where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public long insert(String data) {
        String sql = "insert into image(data, create_time) values(:data, now())";
        MapSqlParameterSource params = new MapSqlParameterSource("data", data);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    //TODO mysql新类型json不能解析{}
    @Override
    public int update(long id, String newData) {
        String sql = "update image set data = :newData where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("newData", newData)
                .addValue("id", id);
        return jdbcTemplate.update(sql, params);
    }

    public int delete(long id) {
        String sql = "delete from image where id = :id";
        return jdbcTemplate.update(sql, Collections.singletonMap("id", id));
    }
}
