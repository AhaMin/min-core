package base_core.design.model;

/**
 * created by ewang on 2018/6/12.
 */
public class OrderDelivery {

    //TODO 调用快递100 api接口

    private final long id;

    private final long orderId;


    public OrderDelivery(long id, long orderId) {
        this.id = id;
        this.orderId = orderId;
    }
}
