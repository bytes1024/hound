package cn.bytes1024.hound.plugins.define;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截上下文信息
 *
 * @author 江浩
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterceptContext implements Serializable {


    private Object target;

    private Method method;

    @Builder.Default
    private Object[] args = {};

    /**
     * 标识当前请求信息，如果过滤掉了 不做后续的处理
     */
    private boolean ignored;

    private Object result;

    private Throwable throwable;

    private Map<String, String> props = new HashMap<>();

}
