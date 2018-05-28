package base_core.user.model;

import com.google.common.reflect.TypeToken;
import common.AbstractDataAttributeEntity;
import common.DataAttributeKey;

import java.util.Date;

/**
 * created by ewang on 2018/3/20.
 */
public class User extends AbstractDataAttributeEntity {

    public static DataAttributeKey<String> KEY_USERNAME = new DataAttributeKey<String>(User.class,
            "username", TypeToken.of(String.class));
    public static DataAttributeKey<Long> KEY_AVATAR = new DataAttributeKey<Long>(User.class,
            "avatarId", TypeToken.of(Long.class));

    private final long id;
    private final String account;
    private final String data;
    private final Date createTime;

    public User(long id, String account, String data, Date createTime) {
        this.id = id;
        this.account = account;
        this.data = data;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getData() {
        return data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUsername() {
        return getDataAttr(KEY_USERNAME).get();
    }

    public Long getAvatarId() {
        return getDataAttr(KEY_AVATAR).get();
    }
}

