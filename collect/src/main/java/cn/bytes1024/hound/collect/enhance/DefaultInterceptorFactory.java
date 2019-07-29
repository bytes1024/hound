package cn.bytes1024.hound.collect.enhance;

import cn.bytes1024.hound.collect.exception.ConstructorException;
import cn.bytes1024.hound.plugins.define.EnhanceContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.InterceptorPluginAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 默认拦截器处理
 *
 * @author 江浩
 */
@Slf4j
public class DefaultInterceptorFactory implements InterceptorFactory {

    private TraceContext traceContext;

    public DefaultInterceptorFactory(TraceContext traceContext) {
        this.traceContext = traceContext;
    }

    @Override
    public Object newInterceptorObject(EnhanceContext enhanceContext) {

        final String interceptorClassName = enhanceContext.getInterceptorClassName();
        final String pluginName = enhanceContext.getRefPluginName();
        try {
            Class<?> aClass = ClassUtils.getClass(interceptorClassName);
            Object object = null;
            try {
                Constructor<?> constructor = aClass.getDeclaredConstructor(TraceContext.class);
                object = constructor.newInstance(this.traceContext);
                invokeMethod(pluginName, aClass, object);

            } catch (NoSuchMethodException ignored) {
            }
            if (Objects.isNull(object)) {
                throw builder(interceptorClassName);
            }

            return object;
        } catch (Exception e) {
            throw builder(interceptorClassName);
        }
    }

    /**
     * invoker {@link InterceptorPluginAware#defineName(String)}
     *
     * @param pluginName :
     * @param aClass     :
     * @param object     :
     * @return : void
     * @author 江浩
     */
    private void invokeMethod(String pluginName, Class<?> aClass, Object object) {

        if (InterceptorPluginAware.class.isAssignableFrom(aClass)) {
            try {
                Method method = aClass.getMethod("defineName", String.class);
                method.invoke(object, pluginName);
            } catch (Exception ignored) {
            }
        }
    }

    private ConstructorException builder(String className) {
        return new ConstructorException(String.format("AroundInterceptor Constructor format: %s",
                className + "(com.support.monitor.agent.core.context.trace.TraceContext  arg0)"));
    }


}
