package base_core.user.dao;

import base_core.user.model.UserPassword;

/**
 * created by ewang on 2018/3/20.
 */
public interface UserPasswordDAO {

    UserPassword getByUser(long userId);

    int insertOrUpdate(long userId, String password, long updateTimeMills);

}
