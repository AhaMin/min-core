package base_core.user.model;

import java.util.Date;

/**
 * created by ewang on 2018/3/20.
 */
public class User {

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
}
