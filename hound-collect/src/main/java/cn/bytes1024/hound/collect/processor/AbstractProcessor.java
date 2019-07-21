package cn.bytes1024.hound.collect.processor;

import cn.bytes1024.hound.commons.option.ConfigOption;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.CountDownLatch;

@Slf4j
public abstract class AbstractProcessor implements Processor {

    @Override
    public void start(ConfigOption configOption, CountDownLatch countDownLatch) {
        StopWatch stopWatch = StopWatch.createStarted();
        this.start(configOption);
        countDownLatch.countDown();
        stopWatch.split();
        log.info("processor {} started use {} ms", this.type(), stopWatch.getSplitTime());
    }

    protected abstract void start(ConfigOption configOption);


}
