package base_core.design.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/6/12.
 */
public enum OrderStatus {

    CONFIRMING(0),//确认中

    PREPARING(1),//准备中

    DELIVERED(2),//已发货

    AFTER_SALE(3),//售后中

    WAIT_EVALUATE(4),//待评价

    COMPLETED(5);//已完成

    private final int value;

    private static Map<Integer, OrderStatus> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (OrderStatus t : values()) {
            OrderStatus exist = valuesMap.put(t.getValue(), t);
            if (exist != null) {
                throw new IllegalStateException("value冲突: " + exist + " " + t);
            }
        }
        valuesMap = Collections.unmodifiableMap(valuesMap);
    }

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatus fromValue(Integer value) {
        return valuesMap.get(value);
    }
}