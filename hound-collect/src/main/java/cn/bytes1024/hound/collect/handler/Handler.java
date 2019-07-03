package cn.bytes1024.hound.collect.handler;

import cn.bytes1024.hound.collect.agent.AgentOption;

import java.util.concurrent.CountDownLatch;

/**
 * 具体的处理器
 *
 * @author 江浩
 */
public interface Handler {

    /**
     * 处理
     *
     * @param agentOption :
     * @return : void
     * @author 江浩
     */
    default void handle(AgentOption agentOption) {
        handle(agentOption, null);
    }

    /**
     * 判定是否启动完全
     *
     * @param agentOption    :
     * @param countDownLatch :
     * @author 江浩
     */
    void handle(AgentOption agentOption, CountDownLatch countDownLatch);


    /**
     * 关闭
     *
     * @author 江浩
     */
    void destroy();
}
