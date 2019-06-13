package com.bytes.hound.collect.module.providers;

import com.alipay.common.tracer.core.SofaTracer;
import com.bytes.hound.collect.context.DefaultTraceContext;
import com.bytes.hound.plugins.define.TraceContext;
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
