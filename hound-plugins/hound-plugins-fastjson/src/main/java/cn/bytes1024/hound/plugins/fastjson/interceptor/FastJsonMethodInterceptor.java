package cn.bytes1024.hound.plugins.fastjson.interceptor;

import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

/**
 * fastjson插件拦截器
 *
 * @author 江浩
 */
public class FastJsonMethodInterceptor extends AbstractMethodAroundInterceptor {
    public FastJsonMethodInterceptor(TraceContext traceContext) {
        super(traceContext);
    }
}
