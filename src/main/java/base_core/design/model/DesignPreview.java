package base_core.design.model;

import base_core.design.constants.DesignSide;
import common.AbstractDataAttributeEntity;
import common.DataAttributeKey;

import java.util.Date;

/**
 * created by ewang on 2018/6/12.
 */
public class DesignPreview extends AbstractDataAttributeEntity {

    public static DataAttributeKey<Long> IMAGE_PREVIEW = new DataAttributeKey<Long>(DesignPreview.class,
            "imagePreview", Long.class);

    public static DataAttributeKey<Long> IMAGE_DETAIL = new DataAttributeKey<Long>(DesignPreview.class,
            "imageDetail", Long.class);

    private final long id;

    private final long designId;

    private final DesignSide designSide;

    private final String data;

    private final Date createTime;

    public DesignPreview(long id, long designId, DesignSide designSide, String data, Date createTime) {
        this.id = id;
        this.designId = designId;
        this.data = data;
        this.designSide = designSide;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getDesignId() {
        return designId;
    }

    public DesignSide getDesignSide() {
        return designSide;
    }

    public String getData() {
        return data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public long getImagePreview() {
        return getDataAttr(IMAGE_PREVIEW).get();
    }

    public long getImageDetail() {
        return getDataAttr(IMAGE_DETAIL).get();
    }
}
