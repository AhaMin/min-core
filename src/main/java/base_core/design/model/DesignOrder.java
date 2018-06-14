package base_core.design.model;

import base_core.design.constants.DesignSize;
import base_core.design.constants.OrderStatus;

import java.util.Date;

/**
 * created by ewang on 2018/6/12.
 */
public class DesignOrder {

    private final long id;

    private final long designId;

    private final long userId;

    private final long addressId;

    private final OrderStatus orderStatus;

    private final double price;

    private final DesignSize designSize;

    private final Date createTime;

    public DesignOrder(long id, long designId, long userId, long addressId, OrderStatus orderStatus, double price, DesignSize designSize, Date createTime) {
        this.id = id;
        this.designId = designId;
        this.userId = userId;
        this.addressId = addressId;
        this.orderStatus = orderStatus;
        this.price = price;
        this.designSize = designSize;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getDesignId() {
        return designId;
    }

    public long getUserId() {
        return userId;
    }

    public long getAddressId() {
        return addressId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public double getPrice() {
        return price;
    }

    public DesignSize getDesignSize() {
        return designSize;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
