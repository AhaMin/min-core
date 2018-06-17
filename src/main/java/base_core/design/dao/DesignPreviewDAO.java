package base_core.design.dao;

import base_core.design.constants.DesignSide;
import base_core.design.model.DesignPreview;

/**
 * created by ewang on 2018/6/12.
 */
public interface DesignPreviewDAO {
    long insert(long designId, DesignSide designSide, String data);

    DesignPreview getById(long id);
}