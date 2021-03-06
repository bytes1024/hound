package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.context.DefaultReporter;
import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.option.ConfigOptionDefine;
import cn.bytes1024.hound.loader.ExtensionLoader;
import cn.bytes1024.hound.transfers.define.TransferDefine;
import cn.bytes1024.hound.transfers.define.buffer.TransferBuffer;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO 服务端默认传输
 * <p>
 * </p>
 *
 * @author 江浩
 */
@Slf4j
public class ServerReporterProvider implements Provider<Reporter> {

    private ExtensionLoader<TransferDefine> transferDefineExtensions = ExtensionLoader.getExtensionLoader(TransferDefine.class);

    private TransferDefine transferDefine = null;

    private TransferBuffer transferBuffer = null;

    private ConfigOption configOption;

    @Inject
    public ServerReporterProvider(ConfigOption configOption, TransferBuffer transferBuffer) {
        this.configOption = configOption;
        this.transferBuffer = transferBuffer;

        boolean transferEnabled = ConfigOptionDefine.isTransferEnabled(configOption);
        if (log.isDebugEnabled()) {
            log.debug("transfer enabled: {}", transferEnabled);
        }

        if (transferEnabled) {
            String transferType = ConfigOptionDefine.getTransferType(configOption);
            this.transferDefine = transferDefineExtensions.getExtension(transferType);
            if (log.isDebugEnabled()) {
                log.debug("transfer plugin : {},{}", transferType, this.transferDefine);
            }
        }
    }

    @Override
    public Reporter get() {
        return new DefaultReporter(this.transferDefine, this.transferBuffer, this.configOption);
    }
}
