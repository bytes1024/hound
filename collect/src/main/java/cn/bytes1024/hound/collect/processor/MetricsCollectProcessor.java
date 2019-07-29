package cn.bytes1024.hound.collect.processor;

import cn.bytes1024.hound.commons.enums.ProcessorType;
import cn.bytes1024.hound.commons.option.ConfigOption;
import lombok.extern.slf4j.Slf4j;

/**
 * 指标数据采集
 *
 * @author 江浩
 */
@Slf4j
public class MetricsCollectProcessor extends AbstractProcessor {

    // TODO: 2019/7/21 时间轮盘
    // TODO: 2019/7/21 任务时间轮盘触发回执上报

    @Override
    public ProcessorType type() {
        return ProcessorType.METRICS;
    }

    @Override
    public void start(ConfigOption configOption) {

    }

    @Override
    public void destroy() {

    }
}
