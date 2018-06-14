package base_core.design.dao;

import base_core.design.model.Design;

/**
 * created by ewang on 2018/6/12.
 */
public interface DesignDAO {
    long insert(long ownerId);

    Design getById(long id);
}
