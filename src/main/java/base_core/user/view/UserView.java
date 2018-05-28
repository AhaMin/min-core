package base_core.user.view;

import base_core.image.view.ImageView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/5/14.
 */
public class UserView {

    @JsonProperty
    private final long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final ImageView image;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final String username;

    @JsonProperty
    private final String account;

    public UserView(long id, ImageView image, String username, String account) {
        this.id = id;
        this.image = image;
        this.username = username;
        this.account = account;
    }

}
