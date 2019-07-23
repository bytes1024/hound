package cn.bytes1024.hound.collect.context;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.option.ConfigOptionDefine;
import cn.bytes1024.hound.transfers.define.DefineTransmitContent;
import cn.bytes1024.hound.transfers.define.TransferDefine;
import cn.bytes1024.hound.transfers.define.TransmitTraceContent;
import cn.bytes1024.hound.transfers.define.buffer.NotifyListener;
import cn.bytes1024.hound.transfers.define.buffer.TransferBuffer;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 默认的上报器
 * <p>
 * 1.上报器目前结合 {@link TransferBuffer} {@link NotifyListener} 使用
 * </p>
 *
 * @author 江浩
 */
@Slf4j
public class DefaultReporter implements Reporter, NotifyListener {

    private TransferDefine transferDefine;

    private TransferBuffer transferBuffer;

    private ConfigOption configOption;

    public DefaultReporter(TransferDefine transferDefine, TransferBuffer transferBuffer, ConfigOption configOption) {
        this.transferDefine = transferDefine;
        this.transferBuffer = transferBuffer;
        this.configOption = configOption;
        if (Objects.nonNull(this.transferBuffer)) {
            log.info("缓冲器启动....");
            this.transferBuffer.register(this);
        }
    }

    @Override
    public String getReporterType() {
        return null;
    }

    @Override
    public void report(SofaTracerSpan sofaTracerSpan) {

        if (Objects.isNull(sofaTracerSpan)) {
            return;
        }

        SofaTracerSpanContext sofaTracerSpanContext = sofaTracerSpan.getSofaTracerSpanContext();

        TransmitTraceContent transmitTraceContent = new TransmitTraceContent()
                .setOperationName(sofaTracerSpan.getOperationName())
                .setTraceId(sofaTracerSpanContext.getTraceId())
                .setParentId(sofaTracerSpanContext.getParentId())
                .setSpanId(sofaTracerSpanContext.getSpanId())
                .setBizBaggage(sofaTracerSpanContext.getBizBaggage())
                .setSysBaggage(sofaTracerSpanContext.getSysBaggage())
                .setStartTime(sofaTracerSpan.getStartTime())
                .setEndTime(sofaTracerSpan.getEndTime())
                .setSampled(sofaTracerSpanContext.isSampled());

        if (ConfigOptionDefine.isOpenTransmitContentView(configOption)) {
            log.info("{}", transmitTraceContent);
        }
        if (Objects.nonNull(transferBuffer)) {
            this.transferBuffer.push(transmitTraceContent);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public <T extends DefineTransmitContent> void notify(T message) {
        if (!Objects.isNull(transferDefine)) {
            this.transferDefine.transmit(configOption, message);
        }
    }
}
