package com.bytes.hound.collect.module.providers;

import com.alipay.common.tracer.core.SofaTracer;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.bytes.hound.collect.agent.AgentOption;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * sofaTrace默认构建
 *
 * @author 江浩
 */
public class SofaTracerProvider implements Provider<SofaTracer> {

    private AgentOption agentOption;

    private Reporter reporter;

    @Inject
    public SofaTracerProvider(AgentOption agentOption, Reporter reporter) {
        this.agentOption = agentOption;
        this.reporter = reporter;
    }

    @Override
    public SofaTracer get() {
        return new SofaTracer.Builder(agentOption.getTracerType())
                .withClientReporter(reporter)
                .withServerReporter(reporter)
                .build();
    }
}
