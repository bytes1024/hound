package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.context.DefaultReporter;
import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.loader.ExtensionLoader;
import cn.bytes1024.hound.transfers.define.TransferDefine;
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

    private ConfigOption configOption;

    @Inject
    public ServerReporterProvider(ConfigOption configOption) {
        this.configOption = configOption;
        this.transferDefine = transferDefineExtensions.getExtension(this.configOption.getTransfer());
        log.info("transfer plugin : {},{}", this.configOption.getTransfer(), this.transferDefine);
    }

    @Override
    public Reporter get() {
        return new DefaultReporter(this.transferDefine, this.configOption);
    }
}
