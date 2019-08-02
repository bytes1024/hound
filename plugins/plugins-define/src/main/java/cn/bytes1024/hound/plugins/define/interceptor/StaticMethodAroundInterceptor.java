package cn.bytes1024.hound.plugins.define.interceptor;

import java.lang.reflect.Method;

/**
 * 静态方法
 *
 * @author 江浩
 */
public interface StaticMethodAroundInterceptor {

    /**
     * before
     *
     * @param clazz          :
     * @param method         :
     * @param allArguments   :
     * @param parameterTypes :
     * @return : void
     * @author 江浩
     */
    void before(Class clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes);

    /**
     * after
     *
     * @param clazz          :
     * @param method         :
     * @param allArguments   :
     * @param parameterTypes :
     * @param ret            :
     * @return : void
     * @author 江浩
     */
    void after(Class clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object ret);

    /**
     * exception
     *
     * @param clazz          :
     * @param method         :
     * @param allArguments   :
     * @param parameterTypes :
     * @param t              :
     * @return : void
     * @author 江浩
     */
    void exception(Class clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes,
                   Throwable t);
}
