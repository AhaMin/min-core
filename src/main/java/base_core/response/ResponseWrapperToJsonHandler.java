package base_core.response;

import codec.JSONCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * created by ewang on 2018/3/20.
 */
public class ResponseWrapperToJsonHandler implements HandlerMethodReturnValueHandler {


    public boolean supportsReturnType(MethodParameter returnType) {
        return ResponseWrapper.class.isAssignableFrom(returnType.getParameterType());
    }


    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (returnValue == null) {
            mavContainer.setRequestHandled(true);
            return;
        }
        ResponseWrapper wrapper = (ResponseWrapper) returnValue;
        if (!wrapper.isSuccess() && StringUtils.isBlank(wrapper.getMessage())) {
            String message = "未知错误";
            if (StringUtils.isNotBlank(message)) {
                wrapper.setMessage(message);
            }
        }

        outputJson((HttpServletResponse) webRequest.getNativeResponse(), JSONCodec.encode(wrapper));

    }

    public static void outputJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
            throw new IOException("out put json fail");
        }
    }
}
