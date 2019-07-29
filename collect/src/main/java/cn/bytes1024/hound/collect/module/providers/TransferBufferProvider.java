package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.transfers.define.buffer.DefaultTransferBuffer;
import cn.bytes1024.hound.transfers.define.buffer.TransferBuffer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferBufferProvider implements Provider<TransferBuffer> {

    private ConfigOption configOption;

    @Inject
    public TransferBufferProvider(ConfigOption configOption) {
        this.configOption = configOption;
    }

    @Override
    public TransferBuffer get() {
        return new DefaultTransferBuffer(this.configOption);
    }
}
