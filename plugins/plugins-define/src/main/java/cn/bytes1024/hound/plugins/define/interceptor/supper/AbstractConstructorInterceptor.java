package cn.bytes1024.hound.plugins.define.interceptor.supper;

import cn.bytes1024.hound.plugins.define.EnhancedDefine;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.ConstructorInterceptor;
import com.alipay.common.tracer.core.span.SofaTracerSpan;

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
