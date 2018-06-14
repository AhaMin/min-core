package base_core.user.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/6/14.
 */
public class UserAddressView {

    @JsonProperty
    private final long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final UserView user;

    @JsonProperty
    private final String province;

    @JsonProperty
    private final String city;

    @JsonProperty
    private final String county;

    @JsonProperty
    private final String street;

    @JsonProperty
    private final String receiverName;

    @JsonProperty
    private final String receiverPhone;

    public UserAddressView(long id, UserView user, String province, String city, String county, String street, String receiverName, String receiverPhone) {
        this.id = id;
        this.user = user;
        this.province = province;
        this.city = city;
        this.county = county;
        this.street = street;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
    }
}
