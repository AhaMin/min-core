package base_core.design.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/6/12.
 */
public enum DesignSize {
    SMALL(0),
    MEDIUM(1),
    LARGE(2),
    EXTRA_LARGE(3);

    private final int value;

    private static Map<Integer, DesignSize> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (DesignSize t : values()) {
            DesignSize exist = valuesMap.put(t.getValue(), t);
            if (exist != null) {
                throw new IllegalStateException("value冲突: " + exist + " " + t);
            }
        }
        valuesMap = Collections.unmodifiableMap(valuesMap);
    }

    DesignSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DesignSize fromValue(Integer value) {
        return valuesMap.get(value);
    }
}