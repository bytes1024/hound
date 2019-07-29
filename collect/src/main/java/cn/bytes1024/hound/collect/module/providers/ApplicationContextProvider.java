package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.context.ApplicationContext;
import cn.bytes1024.hound.collect.context.DefaultApplicationContext;
import cn.bytes1024.hound.collect.module.DefineModule;
import cn.bytes1024.hound.collect.processor.Processor;
import cn.bytes1024.hound.commons.option.ConfigOption;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 江浩
 */
public class ApplicationContextProvider implements Provider<ApplicationContext> {

    private ConfigOption configOption;

    /**
     * 加载处理器
     *
     * @author 江浩
     */
    private List<Processor> processors = new ArrayList<>();

    @Inject
    public ApplicationContextProvider(ConfigOption configOption,
                                      @Named(DefineModule.Alias.AGENT) Processor agentProcessor,
                                      @Named(DefineModule.Alias.METRICS) Processor metricsProcesser
    ) {
        this.configOption = configOption;
        processors.add(agentProcessor);
        processors.add(metricsProcesser);
    }

    @Override
    public ApplicationContext get() {
        return new DefaultApplicationContext(this.configOption, this.processors);
    }
}
