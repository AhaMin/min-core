package base_core.design.model;

import java.util.Date;

/**
 * created by ewang on 2018/6/12.
 */
public class Design {

    private final long id;

    private final long ownerId;

    private final Date createTime;


    public Design(long id, long ownerId, Date createTime) {
        this.id = id;
        this.ownerId = ownerId;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
