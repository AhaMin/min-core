package base_core.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/3/19.
 */
public enum ResponseStatus {
    Ok(200),
    UserIllegal(451), //
    NotFound(404), //
    ServerError(500),
    RequestParamValidationFail(501),
    UploadFail(506); //

    private final int value;

    private static Map<Integer, ResponseStatus> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (ResponseStatus t : values()) {
            ResponseStatus exist = valuesMap.put(t.getValue(), t);
            if (exist != null) {
                throw new IllegalStateException("value冲突: " + exist + " " + t);
            }
        }
        valuesMap = Collections.unmodifiableMap(valuesMap);
    }

    ResponseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ResponseStatus fromValue(Integer value) {
        return valuesMap.get(value);
    }
}