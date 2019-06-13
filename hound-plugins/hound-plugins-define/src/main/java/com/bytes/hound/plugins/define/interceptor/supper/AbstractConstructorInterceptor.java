package com.bytes.hound.plugins.define.interceptor.supper;

import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.bytes.hound.plugins.define.EnhancedDefine;
import com.bytes.hound.plugins.define.TraceContext;
import com.bytes.hound.plugins.define.interceptor.ConstructorInterceptor;

import java.util.Objects;

/**
 * @author 江浩
 */
public class AbstractConstructorInterceptor implements ConstructorInterceptor {

    protected TraceContext traceContext;

    public AbstractConstructorInterceptor(TraceContext traceContext) {
        this.traceContext = traceContext;
    }

    @Override
    public void onConstruct(EnhancedDefine enhancedDefine, Object[] allArguments) {
        SofaTracerSpan sofaTracerSpan = traceContext.getCurrentSpan();
        if (!Objects.isNull(enhancedDefine)) {
            enhancedDefine.setEnhancedInstanceTraceContext(sofaTracerSpan);
        }
    }
}
