package cn.bytes1024.hound.collect.enhance.delegation;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.interceptor.MethodAroundInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 方法拦截
 *
 * @author 江浩
 */
@Slf4j
public class MethodsInterceptWithDelegation {

    private MethodAroundInterceptor methodAroundInterceptor;

    public MethodsInterceptWithDelegation(MethodAroundInterceptor methodAroundInterceptor) {
        this.methodAroundInterceptor = methodAroundInterceptor;
    }

    @RuntimeType
    public Object intercept(
            @This Object object,
            @AllArguments Object[] allArguments,
            @SuperCall Callable<?> callable,
            @Origin Method method) {

        Object result = null;
        Throwable throwable = null;
        InterceptContext interceptContext = InterceptContext.builder()
                .target(object)
                .method(method)
                .args(allArguments)
                .build();
        try {
            methodAroundInterceptor.before(interceptContext);
            result = callable.call();
        } catch (Exception e) {
            throwable = e;
        } finally {
            interceptContext.setResult(result);
            interceptContext.setThrowable(throwable);
            methodAroundInterceptor.after(interceptContext);
        }

        return result;
    }


}
