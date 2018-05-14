package base_core.socket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/4/18.
 */
public class ClientMessage {

    private final long userId;

    private final long toUserId;

    private final String content;

    private final String username;

    private final String toUsername;

    @JsonCreator
    public ClientMessage(@JsonProperty("userId") long userId,
                         @JsonProperty("toUserId") long toUserId,
                         @JsonProperty("content") String content,
                         @JsonProperty("username") String username,
                         @JsonProperty("toUsername") String toUsername) {
        this.userId = userId;
        this.toUserId = toUserId;
        this.content = content;
        this.username = username;
        this.toUsername = toUsername;
    }

    public long getUserId() {
        return userId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getToUsername() {
        return toUsername;
    }
}
