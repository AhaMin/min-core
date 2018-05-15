package base_core.session.view;

import base_core.user.view.UserView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * created by ewang on 2018/5/15.
 */
public class MessageView {

    @JsonProperty
    private final UserView user;

    @JsonProperty
    private final UserView toUser;

    @JsonProperty
    private final String content;

    @JsonProperty
    private final Date createTime;

    public MessageView(UserView user, UserView toUser, String content, Date createTime) {
        this.user = user;
        this.toUser = toUser;
        this.content = content;
        this.createTime = createTime;
    }
}
