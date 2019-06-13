package com.bytes.hound.plugins.define.interceptor.supper;

import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import lombok.NonNull;

/**
 * 远程传输
 *
 * @author 江浩
 */
public interface RemoteTransmission<H> {

    /**
     * 传输
     *
     * @param handler     :
     * @param key         :
     * @param spanContext :
     * @return : void
     * @author 江浩
     */
    default void transmission(@NonNull H handler, @NonNull String key, @NonNull SofaTracerSpanContext spanContext) {

    }

    /**
     * 接收处理信息
     *
     * @param handler :
     * @param key     :
     * @return : com.alipay.common.tracer.core.context.span.SofaTracerSpanContext
     * @author 江浩
     */
    default SofaTracerSpanContext receive(@NonNull H handler, @NonNull String key) {
        return null;
    }
}
