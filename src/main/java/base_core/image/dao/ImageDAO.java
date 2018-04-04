package base_core.image.dao;

import base_core.image.model.Image;

/**
 * created by ewang on 2018/3/23.
 */
public interface ImageDAO {
    Image getById(long id);

    long insert(String data);

    int update(long id, String newData);

    int delete(long id);
}
