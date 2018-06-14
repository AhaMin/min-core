package base_core.design.view;

import base_core.design.constants.DesignSize;
import base_core.design.constants.OrderStatus;
import base_core.user.view.UserAddressView;
import base_core.user.view.UserView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/6/14.
 */
public class DesignOrderView {

    @JsonProperty
    private final long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final DesignView design;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final UserView user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final UserAddressView userAddress;

    @JsonProperty
    private final DesignSize designSize;

    @JsonProperty
    private final double price;

    @JsonProperty
    private final OrderStatus orderStatus;

    public DesignOrderView(long id, DesignView design, UserView user, UserAddressView userAddress, DesignSize designSize, double price, OrderStatus orderStatus) {
        this.id = id;
        this.design = design;
        this.user = user;
        this.userAddress = userAddress;
        this.designSize = designSize;
        this.price = price;
        this.orderStatus = orderStatus;
    }
}
