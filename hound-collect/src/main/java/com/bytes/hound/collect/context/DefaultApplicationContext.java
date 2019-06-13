package com.bytes.hound.collect.context;

import com.bytes.hound.collect.agent.AgentOption;
import com.bytes.hound.collect.handler.Handler;
import com.bytes.hound.commons.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 默认容器环境
 * @author 江浩
 */
@Slf4j
public class DefaultApplicationContext implements ApplicationContext {

    private AgentOption agentOption;

    private List<Handler> handlers;

    private ThreadPoolExecutor poolExecutor = ThreadPoolUtils.newFixedThreadPool(3,DefaultApplicationContext.class.getName());

    public DefaultApplicationContext( AgentOption agentOption,
                                      List<Handler> handlers){
        this.agentOption = agentOption;
        this.handlers = handlers;
    }

    @Override
    public void start() {

        if(CollectionUtils.isNotEmpty(handlers)) {

            CountDownLatch countDownLatch = new CountDownLatch(this.handlers.size());

            handlers.forEach(handler -> poolExecutor.execute(() -> {
                try{
                    handler.handle(agentOption,countDownLatch);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("handler start error : {}",e);

                this.close();

            }
        }

    }

    @Override
    public void close() {
        if(CollectionUtils.isNotEmpty(handlers)){
            handlers.forEach(Handler::destroy);
        }
    }
}
