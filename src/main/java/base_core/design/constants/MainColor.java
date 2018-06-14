package base_core.design.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/6/12.
 */
public enum MainColor {
    WHITE(0),
    BLACK(1);

    private final int value;

    private static Map<Integer, MainColor> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (MainColor t : values()) {
            MainColor exist = valuesMap.put(t.getValue(), t);
            if (exist != null) {
                throw new IllegalStateException("value冲突: " + exist + " " + t);
            }
        }
        valuesMap = Collections.unmodifiableMap(valuesMap);
    }

    MainColor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MainColor fromValue(Integer value) {
        return valuesMap.get(value);
    }
}