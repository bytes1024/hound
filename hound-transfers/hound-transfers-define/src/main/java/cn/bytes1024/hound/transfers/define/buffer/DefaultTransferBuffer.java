package cn.bytes1024.hound.transfers.define.buffer;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.transfers.define.AbstractTransmitContent;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的缓冲区域
 *
 * @author 江浩
 */
public class DefaultTransferBuffer implements TransferBuffer {

    private List<NotifyListener> notifyListeners = new ArrayList<>();


    private ConfigOption configOption;

    public DefaultTransferBuffer(ConfigOption configOption) {

    }


    @Override
    public void register(NotifyListener notifyListener) {
        notifyListeners.add(notifyListener);
    }

    @Override
    public <T extends AbstractTransmitContent> void push(T transmitContent) {

    }

    @Override
    public List<NotifyListener> notifyListener() {
        return this.notifyListeners;
    }
}
