package user.dao;

import user.model.UserPassword;

/**
 * created by ewang on 2018/3/20.
 */
public interface UserPasswordDAO {
    UserPassword getByUser(long userId);
}
