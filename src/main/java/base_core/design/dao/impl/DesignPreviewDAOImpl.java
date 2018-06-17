package base_core.design.dao.impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.design.constants.DesignSide;
import base_core.design.dao.DesignPreviewDAO;
import base_core.design.model.DesignPreview;
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
public class DesignPreviewDAOImpl implements DesignPreviewDAO, RowMapper<DesignPreview> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.Design));
    }

    @Override
    public DesignPreview mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DesignPreview(rs.getLong("id"),
                rs.getLong("design_id"),
                DesignSide.fromValue(rs.getInt("side")),
                rs.getString("data"),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    @Override
    public long insert(long designId, DesignSide designSide, String data) {
        String sql = "insert into design_preview(design_id,data,side,create_time) " +
                "values(:designId,:data,:side,now())";
        MapSqlParameterSource params = new MapSqlParameterSource("designId", designId)
                .addValue("data", data)
                .addValue("side", designSide.getValue());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public DesignPreview getById(long id) {
        String sql = "select * from design_preview where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
