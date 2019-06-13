package com.bytes.hound.collect.agent;

import com.bytes.hound.collect.module.CollectModuleFactory;

import java.lang.instrument.Instrumentation;

/**
 * 收集器探针
 * @author 江浩
 */
public class CollectAgent {

    /**
     * agent 拦截
     *
     * @param agentArgs
     * @param instrumentation
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        CollectModuleFactory applicationContextModuleFactory = new CollectModuleFactory();
        applicationContextModuleFactory.init(agentArgs, instrumentation);
    }
}
