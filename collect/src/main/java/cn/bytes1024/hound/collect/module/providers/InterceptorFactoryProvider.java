package cn.bytes1024.hound.collect.module.providers;


import cn.bytes1024.hound.collect.enhance.DefaultInterceptorFactory;
import cn.bytes1024.hound.collect.enhance.InterceptorFactory;
import cn.bytes1024.hound.plugins.define.TraceContext;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 拦截器处理
 *
 * @author 江浩
 */
public class InterceptorFactoryProvider implements Provider<InterceptorFactory> {

    private TraceContext traceContext;

    @Inject
    public InterceptorFactoryProvider(TraceContext traceContext) {
        this.traceContext = traceContext;
    }

    @Override
    public InterceptorFactory get() {
        return new DefaultInterceptorFactory(this.traceContext);
    }
}
