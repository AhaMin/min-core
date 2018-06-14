package base_core.design.view;

import base_core.user.view.UserView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/6/14.
 */
public class DesignView {

    @JsonProperty
    private final long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final UserView owner;

    public DesignView(long id, UserView owner) {
        this.id = id;
        this.owner = owner;
    }
}
