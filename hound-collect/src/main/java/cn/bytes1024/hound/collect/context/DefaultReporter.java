package cn.bytes1024.hound.collect.context;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.transfers.define.TransferDefine;
import cn.bytes1024.hound.transfers.define.TransmitObject;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 默认的上报器
 * <p>以插件的方式加载传输器</p>
 * <p>TODO 上报数据需要一个缓冲区</p>
 *
 * @author 江浩
 */
@Slf4j
public class DefaultReporter implements Reporter {

    private TransferDefine transferDefine;

    private ConfigOption configOption;

    public DefaultReporter(TransferDefine transferDefine, ConfigOption configOption) {
        this.transferDefine = transferDefine;
        this.configOption = configOption;
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
        TransmitObject transmitObject = new TransmitObject()
                .setOperationName(sofaTracerSpan.getOperationName())
                .setTraceId(sofaTracerSpanContext.getTraceId())
                .setParentId(sofaTracerSpanContext.getParentId())
                .setSpanId(sofaTracerSpanContext.getSpanId())
                .setBizBaggage(sofaTracerSpanContext.getBizBaggage())
                .setSysBaggage(sofaTracerSpanContext.getSysBaggage())
                .setStartTime(sofaTracerSpan.getStartTime())
                .setEndTime(sofaTracerSpan.getEndTime())
                .setSampled(sofaTracerSpanContext.isSampled());

        this.transferDefine.transmit(configOption, transmitObject);

    }

    @Override
    public void close() {

    }
}
