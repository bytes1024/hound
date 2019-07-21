package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.option.ConfigOptionDefine;
import com.alipay.common.tracer.core.SofaTracer;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * sofaTrace默认构建
 *
 * @author 江浩
 */
public class SofaTracerProvider implements Provider<SofaTracer> {

    private ConfigOption configOption;

    private Reporter reporter;

    @Inject
    public SofaTracerProvider(ConfigOption configOption, Reporter reporter) {
        this.configOption = configOption;
        this.reporter = reporter;
    }

    @Override
    public SofaTracer get() {
        return new SofaTracer.Builder(configOption.getOption(ConfigOptionDefine.TRACER_TYPE, null))
                .withClientReporter(reporter)
                .withServerReporter(reporter)
                .build();
    }
}
