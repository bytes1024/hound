package cn.bytes1024.hound.plugins.druid.interceptor;

import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

/**
 * druid 默认拦截器
 *
 * @author 江浩
 */
public class DruidPluginInterceptor extends AbstractMethodAroundInterceptor {
    public DruidPluginInterceptor(TraceContext traceContext) {
        super(traceContext);
    }
}
