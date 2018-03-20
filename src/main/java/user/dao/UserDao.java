package user.dao;

import user.model.User;

/**
 * created by ewang on 2018/3/20.
 */
public interface UserDao {
    User getById(long id);
}
