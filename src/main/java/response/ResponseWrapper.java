package response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/3/19.
 */
public class ResponseWrapper {

    private final ResponseStatus status;

    private String message;

    private final Map<String, Object> data = new HashMap<>();

    /**
     * EMPTY返回一个新的对象，这个对象不允许修改
     */
    public static final ResponseWrapper EMPTY = new ResponseWrapper() {
        @Override
        public <T> ResponseWrapper addObject(String key, T value) {
            throw new UnsupportedOperationException();
        }
    };

    /**
     * 默认状态为ok
     */
    public ResponseWrapper() {
        this.status = ResponseStatus.Ok;
        message = null;
    }

    public ResponseWrapper(ResponseStatus status) {
        this.status = status;
    }

    public ResponseWrapper(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public <T> ResponseWrapper addObject(String key, T value) {
        if (key != null) {
            data.put(key, data);
        }
        return this;
    }

    @JsonInclude(Include.NON_NULL)
    public boolean isSuccess() {
        return status == ResponseStatus.Ok;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    @JsonInclude(Include.NON_NULL)
    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(data);
    }
}
