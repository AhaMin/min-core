package base_core.session.view;

import base_core.user.view.UserView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * created by ewang on 2018/5/15.
 */
public class SessionView {

    @JsonProperty
    private final UserView toUser;

    @JsonProperty
    private final String latestMessage;

    @JsonProperty
    private final Date updateTime;

    @JsonProperty
    private final int unread;


    public SessionView(UserView toUser, String latestMessage, Date updateTime, int unread) {
        this.toUser = toUser;
        this.latestMessage = latestMessage;
        this.updateTime = updateTime;
        this.unread = unread;
    }
}
