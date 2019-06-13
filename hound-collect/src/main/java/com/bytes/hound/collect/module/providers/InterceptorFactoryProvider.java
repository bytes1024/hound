package com.bytes.hound.collect.module.providers;


import com.bytes.hound.collect.enhance.DefaultInterceptorFactory;
import com.bytes.hound.collect.enhance.InterceptorFactory;
import com.bytes.hound.plugins.define.TraceContext;
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
