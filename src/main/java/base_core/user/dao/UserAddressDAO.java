package base_core.user.dao;

import base_core.user.model.UserAddress;

import java.util.List;

/**
 * created by ewang on 2018/6/13.
 */
public interface UserAddressDAO {
    UserAddress getById(long addressId);

    long insert(long userId, String data);

    List<UserAddress> findByUser(long userId);
}
