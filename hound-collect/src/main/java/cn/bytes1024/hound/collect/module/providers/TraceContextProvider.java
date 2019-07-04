package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.context.DefaultTraceContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.filter.TraceContextFilterOption;
import com.alipay.common.tracer.core.SofaTracer;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * TraceContext provider
 *
 * @author 江浩
 */
public class TraceContextProvider implements Provider<TraceContext> {

    private SofaTracer sofaTracer;

    private TraceContextFilterOption traceContextFilterOption;

    @Inject
    public TraceContextProvider(SofaTracer sofaTracer, TraceContextFilterOption traceContextFilterOption) {
        this.sofaTracer = sofaTracer;
        this.traceContextFilterOption = traceContextFilterOption;
    }

    @Override
    public TraceContext get() {
        return new DefaultTraceContext(sofaTracer, this.traceContextFilterOption);
    }
}
