package com.bytes.hound.collect.handler;

import com.bytes.hound.collect.agent.AgentOption;

import java.util.concurrent.CountDownLatch;

/**
 * 具体的处理器
 * @author 江浩
 */
public interface Handler {

    /**
     * 处理
     * @param agentOption :
     * @return : void
     * @author 江浩
     */
    default void handle(AgentOption agentOption){
        handle(agentOption,null);
    }

    /**
     * 判定是否启动完全
     * @param agentOption : 
     * @param countDownLatch :
     * @return : void
     * @author 江浩
     */
    void handle(AgentOption agentOption, CountDownLatch countDownLatch);


    /**
     * 关闭
     * @return : void
     * @author 江浩
     */
    void destroy();
}
