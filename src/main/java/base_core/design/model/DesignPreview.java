package base_core.design.model;

import base_core.design.constants.DesignSide;

import java.util.Date;

/**
 * created by ewang on 2018/6/12.
 */
public class DesignPreview {
    private final long id;

    private final long designId;

    private final long previewImageId;

    private final long detailImageId;

    private final DesignSide designSide;

    private final Date createTime;

    public DesignPreview(long id, long designId, long previewImageId, long detailImageId, DesignSide designSide, Date createTime) {
        this.id = id;
        this.designId = designId;
        this.previewImageId = previewImageId;
        this.detailImageId = detailImageId;
        this.designSide = designSide;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getDesignId() {
        return designId;
    }

    public long getPreviewImageId() {
        return previewImageId;
    }

    public long getDetailImageId() {
        return detailImageId;
    }

    public DesignSide getDesignSide() {
        return designSide;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
