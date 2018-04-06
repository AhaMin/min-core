package base_core.constants.helper;

import base_core.constants.model.Constants;
import base_core.constants.model.DBKeys;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * created by ewang on 2018/4/6.
 */
public class DBHelper {

    public static DataSource getDataSource(DBKeys key) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://" + key.getValue());
        dataSource.setUsername(Constants.DBUsername.getValue());
        dataSource.setPassword(Constants.DBPwd.getValue());
        return dataSource;
    }
}
