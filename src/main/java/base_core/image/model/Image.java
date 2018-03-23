package base_core.image.model;

import common.AbstractDataAttributeEntity;
import common.DataAttributeKey;

import java.util.Date;

/**
 * created by ewang on 2018/3/23.
 */
public class Image extends AbstractDataAttributeEntity {

    public static final DataAttributeKey<String> KEY_PATH = new DataAttributeKey<>(Image.class, "path", String.class);

    public static final DataAttributeKey<String> KEY_TYPE = new DataAttributeKey<>(Image.class, "type", String.class);

    public static final DataAttributeKey<Integer> KEY_WIDTH = new DataAttributeKey<>(Image.class, "width", Integer.class);

    public static final DataAttributeKey<Integer> KEY_HEIGHT = new DataAttributeKey<>(Image.class, "height", Integer.class);

    private final long id;
    private final String data;
    private final Date createTime;

    public Image(long id, String data, Date createTime) {
        this.id = id;
        this.data = data;
        this.createTime = createTime;
    }

    public String getPath() {
        return getDataAttr(KEY_PATH).get();
    }

    public String getType() {
        return getDataAttr(KEY_TYPE).get();
    }

    public int getWidth() {
        return getDataAttr(KEY_WIDTH).get();
    }

    public int getHeight() {
        return getDataAttr(KEY_HEIGHT).get();
    }

    public long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
