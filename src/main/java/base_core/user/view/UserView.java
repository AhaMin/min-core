package base_core.user.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/5/14.
 */
public class UserView {

    @JsonProperty
    private final long id;

    @JsonProperty
    private final String username;

    @JsonProperty
    private final String account;

    public UserView(long id, String username, String account) {
        this.id = id;
        this.username = username;
        this.account = account;
    }

}
