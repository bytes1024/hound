package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.context.DefaultTraceContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import com.alipay.common.tracer.core.SofaTracer;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TraceContextProvider implements Provider<TraceContext> {

    private SofaTracer sofaTracer;

    @Inject
    public TraceContextProvider(SofaTracer sofaTracer) {
        this.sofaTracer = sofaTracer;
    }

    @Override
    public TraceContext get() {
        return new DefaultTraceContext(sofaTracer);
    }
}
