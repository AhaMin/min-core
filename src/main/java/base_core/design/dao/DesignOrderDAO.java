package base_core.design.dao;

import base_core.design.constants.DesignSize;
import base_core.design.model.DesignOrder;

/**
 * created by ewang on 2018/6/12.
 */
public interface DesignOrderDAO {
    long insert(long designId, long userId, long addressId, double price, DesignSize designSize);

    DesignOrder getById(long designId);
}
