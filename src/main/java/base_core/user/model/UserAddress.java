package base_core.user.model;

import common.AbstractDataAttributeEntity;
import common.DataAttributeKey;

import java.util.Date;

/**
 * created by ewang on 2018/6/12.
 */
public class UserAddress extends AbstractDataAttributeEntity {

    public static DataAttributeKey<String> KEY_PROVINCE = new DataAttributeKey<String>(UserAddress.class,
            "province", String.class);

    public static DataAttributeKey<String> KEY_CITY = new DataAttributeKey<String>(UserAddress.class,
            "city", String.class);

    public static DataAttributeKey<String> KEY_COUNTY = new DataAttributeKey<String>(UserAddress.class,
            "county", String.class);

    public static DataAttributeKey<String> KEY_STREET = new DataAttributeKey<String>(UserAddress.class,
            "detailStreet", String.class);

    public static DataAttributeKey<String> KEY_RECEIVER_NAME = new DataAttributeKey<String>(UserAddress.class,
            "receiverName", String.class);

    public static DataAttributeKey<String> KEY_RECEIVER_PHONE = new DataAttributeKey<String>(UserAddress.class,
            "receiverPhone", String.class);

    private final long id;

    private final long userId;

    private final String data;

    private final Date createTime;


    public UserAddress(long id, long userId, String data, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.data = data;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getData() {
        return data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getProvince() {
        return getDataAttr(KEY_PROVINCE).get();
    }

    public String getCity() {
        return getDataAttr(KEY_CITY).get();
    }

    public String getCounty() {
        return getDataAttr(KEY_COUNTY).get();
    }

    public String getStreet() {
        return getDataAttr(KEY_STREET).get();
    }

    public String getReceiverName() {
        return getDataAttr(KEY_RECEIVER_NAME).get();
    }

    public String getReceiverPhone() {
        return getDataAttr(KEY_RECEIVER_PHONE).get();
    }
}
