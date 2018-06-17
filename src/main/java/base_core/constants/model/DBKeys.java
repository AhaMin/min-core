package base_core.constants.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/4/6.
 */
public enum DBKeys {
    User("127.0.0.1:3306/user"),
    Image("127.0.0.1:3306/image"),
    Session("127.0.0.1:3306/session"),
    Design("127.0.0.1:3306/design");

    private final String value;

    private static Map<String, DBKeys> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (DBKeys t : values()) {
            DBKeys exist = valuesMap.put(t.getValue(), t);
            if (exist != null) {
                throw new IllegalStateException("value冲突: " + exist + " " + t);
            }
        }
        valuesMap = Collections.unmodifiableMap(valuesMap);
    }

    DBKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DBKeys fromValue(String value) {
        return valuesMap.get(value);
    }
}