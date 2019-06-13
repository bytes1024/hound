package com.bytes.hound.collect.context;

/**
 * 初始容器环境
 * @author 江浩
 */
public interface ApplicationContext {

    /**
     * 容器启动
     * @return : void
     * @author 江浩
     */
    void start();

    /**
     * 容器关闭
     * @return : void
     * @author 江浩
     */
    void close();
}
