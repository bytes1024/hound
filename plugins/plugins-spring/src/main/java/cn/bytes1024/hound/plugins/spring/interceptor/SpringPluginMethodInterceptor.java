package cn.bytes1024.hound.plugins.spring.interceptor;


import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

/**
 * spring 相关拦截器
 *
 * @author 江浩
 */
public class SpringPluginMethodInterceptor extends AbstractMethodAroundInterceptor {

    public SpringPluginMethodInterceptor(TraceContext traceContext) {
        super(traceContext);
    }


}
