package cn.bytes1024.hound.plugins.gson.intercepotr;

import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

/**
 * Gson插件拦截器
 *
 * @author 江浩
 */
public class GsonMethodInterceptor extends AbstractMethodAroundInterceptor {
    public GsonMethodInterceptor(TraceContext traceContext) {
        super(traceContext);
    }
}
