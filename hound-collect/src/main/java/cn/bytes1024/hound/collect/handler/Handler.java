package cn.bytes1024.hound.collect.handler;

import cn.bytes1024.hound.commons.option.ConfigOption;

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
     * @param configOption :
     * @return : void
     * @author 江浩
     */
    default void handle(ConfigOption configOption) {
        handle(configOption, null);
    }

    /**
     * 判定是否启动完全
     *
     * @param configOption   :
     * @param countDownLatch :
     * @author 江浩
     */
    void handle(ConfigOption configOption, CountDownLatch countDownLatch);


    /**
     * 关闭
     *
     * @author 江浩
     */
    void destroy();
}
