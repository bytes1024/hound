package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.context.ApplicationContext;
import cn.bytes1024.hound.collect.context.DefaultApplicationContext;
import cn.bytes1024.hound.collect.handler.Handler;
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
    private List<Handler> handlers = new ArrayList<>();

    @Inject
    public ApplicationContextProvider(ConfigOption configOption,
                                      @Named("agent") Handler agentHandler) {
        this.configOption = configOption;
        handlers.add(agentHandler);
    }

    @Override
    public ApplicationContext get() {
        return new DefaultApplicationContext(this.configOption, this.handlers);
    }
}
