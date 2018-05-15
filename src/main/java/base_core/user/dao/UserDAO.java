package base_core.user.dao;

import base_core.user.model.User;

/**
 * created by ewang on 2018/3/20.
 */
public interface UserDAO {

    User getById(long id);

    User getByAccount(String account);

    long insert(String account, String data);

    int updateData(long userId, String newData, String oldData);
}
