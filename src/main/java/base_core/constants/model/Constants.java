package base_core.constants.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/3/23.
 */
public enum Constants {
    ImagePath("/Users/ewang/Min-ImagePath/upload/");

    private final String value;

    private static Map<String, Constants> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (Constants t : values()) {
            Constants exist = valuesMap.put(t.getValue(), t);
            if (exist != null) {
                throw new IllegalStateException("value冲突: " + exist + " " + t);
            }
        }
        valuesMap = Collections.unmodifiableMap(valuesMap);
    }

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Constants fromValue(Integer value) {
        return valuesMap.get(value);
    }
}