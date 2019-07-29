package cn.bytes1024.hound.collect.context;

import cn.bytes1024.hound.collect.processor.Processor;
import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 默认容器环境
 *
 * @author 江浩
 */
@Slf4j
public class DefaultApplicationContext implements ApplicationContext {

    private ConfigOption configOption;

    private List<Processor> processors;

    private ThreadPoolExecutor poolExecutor = ThreadPoolUtils.newFixedThreadPool(3, DefaultApplicationContext.class.getName());

    public DefaultApplicationContext(ConfigOption configOption,
                                     List<Processor> processors) {
        this.configOption = configOption;
        this.processors = processors;
    }

    @Override
    public void start() {

        if (CollectionUtils.isNotEmpty(processors)) {

            CountDownLatch countDownLatch = new CountDownLatch(this.processors.size());

            processors.forEach(handler -> poolExecutor.execute(() -> {
                try {
                    handler.start(configOption, countDownLatch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            try {
                countDownLatch.await(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("handler start error : {}", e);
                this.close();
            }
        }

    }

    @Override
    public void close() {
        if (CollectionUtils.isNotEmpty(processors)) {
            processors.forEach(Processor::destroy);
        }
    }
}
