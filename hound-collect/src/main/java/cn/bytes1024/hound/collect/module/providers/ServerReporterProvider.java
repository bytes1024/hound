package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.agent.AgentOption;
import cn.bytes1024.hound.collect.context.DefaultReporter;
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

    private AgentOption agentOption;

    @Inject
    public ServerReporterProvider(AgentOption agentOption) {
        this.agentOption = agentOption;
        this.transferDefine = transferDefineExtensions.getExtension(this.agentOption.getTransfer());
        log.info("transfer plugin : {},{}", this.agentOption.getTransfer(), this.transferDefine);
    }

    @Override
    public Reporter get() {
        return new DefaultReporter(this.transferDefine, this.agentOption);
    }
}
