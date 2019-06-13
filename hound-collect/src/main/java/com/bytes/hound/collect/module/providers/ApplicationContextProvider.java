package com.bytes.hound.collect.module.providers;

import com.bytes.hound.collect.agent.AgentOption;
import com.bytes.hound.collect.context.ApplicationContext;
import com.bytes.hound.collect.context.DefaultApplicationContext;
import com.bytes.hound.collect.handler.Handler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 江浩
 */
public class ApplicationContextProvider implements Provider<ApplicationContext> {

    private AgentOption agentOption;

    /**
     * 加载处理器
     * @author 江浩
     */
    private List<Handler> handlers = new ArrayList<>();

    @Inject
    public ApplicationContextProvider(AgentOption agentOption,
                                      @Named("agent") Handler agentHandler) {
        this.agentOption = agentOption;
        handlers.add(agentHandler);
    }

    @Override
    public ApplicationContext get() {
        return new DefaultApplicationContext(this.agentOption,this.handlers);
    }
}
