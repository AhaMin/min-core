package base_core.session.model;

import java.util.Date;

/**
 * created by ewang on 2018/5/14.
 */
public class Session {

    private final long id;

    private final long userId;

    private final long toUserId;

    private final Date createTime;

    private final Date updateTime;

    private final int unread;

    public Session(long id, long userId, long toUserId, Date createTime, Date updateTime, int unread) {
        this.id = id;
        this.userId = userId;
        this.toUserId = toUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.unread = unread;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public int getUnread() {
        return unread;
    }
}
