package cn.bytes1024.hound.collect.enhance.delegation;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.interceptor.MethodAroundInterceptor;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * 方法拦截
 *
 * @author 江浩
 */
public class MethodsInterceptWithOverrideArgsDelegation {

    private MethodAroundInterceptor methodAroundInterceptor;

    public MethodsInterceptWithOverrideArgsDelegation(MethodAroundInterceptor methodAroundInterceptor) {
        this.methodAroundInterceptor = methodAroundInterceptor;
    }

    @RuntimeType
    public Object intercept(
            @This Object object,
            @Origin Method method,
            @AllArguments Object[] allArguments,
            @Morph OverrideCallable callable) throws Exception {

        Object result = null;
        Throwable throwable = null;

        InterceptContext interceptContext = InterceptContext.builder()
                .target(object)
                .method(method)
                .args(allArguments)
                .build();

        try {
            methodAroundInterceptor.before(interceptContext);
            result = callable.invoker(allArguments);
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
