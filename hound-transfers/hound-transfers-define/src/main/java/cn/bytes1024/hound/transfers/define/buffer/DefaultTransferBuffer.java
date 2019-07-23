package cn.bytes1024.hound.transfers.define.buffer;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.util.ThreadPoolUtils;
import cn.bytes1024.hound.transfers.define.DefineTransmitContent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 默认的缓冲区域
 *
 * @author 江浩
 */
@Slf4j
public class DefaultTransferBuffer implements TransferBuffer, Runnable {

    private List<NotifyListener> notifyListeners = new ArrayList<>();

    private ThreadPoolExecutor poolExecutor = ThreadPoolUtils.newFixedThreadPool(5, this.getClass().getName());

    private final Queue<DefineTransmitContent> queue = new ConcurrentLinkedQueue<>();

    private ConfigOption configOption;

    public DefaultTransferBuffer(ConfigOption configOption) {
        this.configOption = configOption;
        poolExecutor.execute(this);
    }

    @Override
    public void register(NotifyListener notifyListener) {
        notifyListeners.add(notifyListener);
    }

    @Override
    public <T extends DefineTransmitContent> void push(T transmitContent) {
        poolExecutor.execute(() -> {
            try {
                boolean bool = queue.add(transmitContent);
                if (!bool && log.isDebugEnabled()) {
                    log.debug("数据缓冲添加失败: {}->{}", bool, transmitContent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<NotifyListener> notifyListener() {
        return this.notifyListeners;
    }

    @Override
    public void run() {
        while (true) {

            DefineTransmitContent content = queue.poll();
            if (Objects.nonNull(content)) {
                try {
                    for (NotifyListener notifyListener : notifyListeners) {
                        notifyListener.notify(content);
                    }
                    TimeUnit.NANOSECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
