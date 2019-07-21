package cn.bytes1024.hound.collect.processor;

import cn.bytes1024.hound.commons.enums.ProcessorStatus;
import cn.bytes1024.hound.commons.enums.ProcessorType;
import cn.bytes1024.hound.commons.option.ConfigOption;

import java.util.concurrent.CountDownLatch;

/**
 * 具体的处理器
 *
 * @author 江浩
 */
public interface Processor {


    /**
     * 处理器类型
     *
     * @return : cn.bytes1024.hound.commons.enums.ProcessorType
     * @author 江浩
     */
    ProcessorType type();

    /**
     * 处理器状态
     *
     * @return : cn.bytes1024.hound.commons.enums.ProcessorStatus
     * @author 江浩
     */
    default ProcessorStatus status() {
        return ProcessorStatus.STOP;
    }

    /**
     * 判定是否启动完全
     *
     * @param configOption   :
     * @param countDownLatch :
     * @author 江浩
     */
    void start(ConfigOption configOption, CountDownLatch countDownLatch);

    /**
     * 关闭
     *
     * @author 江浩
     */
    void destroy();
}
