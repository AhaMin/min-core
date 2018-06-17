package base_core.design.dao.impl;

import base_core.constants.helper.DBHelper;
import base_core.constants.model.DBKeys;
import base_core.design.constants.DesignSize;
import base_core.design.constants.OrderStatus;
import base_core.design.dao.DesignOrderDAO;
import base_core.design.model.DesignOrder;
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
public class DesignOrderDAOImpl implements DesignOrderDAO, RowMapper<DesignOrder> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new NamedParameterJdbcTemplate(DBHelper.getDataSource(DBKeys.Design));
    }

    @Override
    public DesignOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DesignOrder(rs.getLong("id"),
                rs.getLong("design_id"),
                rs.getLong("user_id"),
                rs.getLong("address_id"),
                OrderStatus.fromValue(rs.getInt("status")),
                rs.getDouble("price"),
                DesignSize.fromValue(rs.getInt("size")),
                new Date(rs.getTimestamp("create_time").getTime()));
    }

    @Override
    public long insert(long designId, long userId, long addressId, double price, DesignSize designSize) {
        String sql = "insert into design_order(design_id,user_id,address_id,status,price,`size`,create_time) " +
                "values(:designId,:userId,:addressId,:status,:price,:size,now())";
        MapSqlParameterSource params = new MapSqlParameterSource("designId", designId)
                .addValue("userId", userId)
                .addValue("addressId", addressId)
                .addValue("status", OrderStatus.CONFIRMING.getValue())
                .addValue("price", price)
                .addValue("size", designSize.getValue());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public DesignOrder getById(long orderId) {
        String sql = "select * from design_order where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", orderId), this);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


}
