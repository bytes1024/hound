package com.bytes.hound.plugins.webflux.interceptor;


import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.bytes.hound.plugins.define.InterceptContext;
import com.bytes.hound.plugins.define.TraceContext;
import com.bytes.hound.plugins.define.interceptor.supper.AbstractTransmissionMethodAroundInterceptor;
import com.bytes.hound.plugins.define.interceptor.supper.RemoteTransmission;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Objects;

/**
 * @author 江浩
 */
public class OnInboundNextMethodInterceptor extends AbstractTransmissionMethodAroundInterceptor<HttpRequest> {

    static final String IGNORED_URL = ".*favicon.ico.*";

    public OnInboundNextMethodInterceptor(TraceContext traceContext) {
        super(traceContext);

        setRemoteTransmission(new RemoteTransmission<HttpRequest>() {
            @Override
            public SofaTracerSpanContext receive(HttpRequest handler, String key) {
                String spanContext = handler.headers().get(key);
                return convert(spanContext);
            }
        });
    }


    @Override
    public void before(InterceptContext interceptContext) {
        Object[] args = interceptContext.getArgs();

        if (Objects.isNull(args) || args.length <= 0) {
            return;
        }

        Object arg1 = args[1];
        if (!(arg1 instanceof HttpRequest)) {
            interceptContext.setIgnored(true);
            return;
        }

        HttpRequest httpRequest = (HttpRequest) arg1;
        String URI = httpRequest.uri();

        interceptContext.setIgnored(URI.matches(IGNORED_URL));
        if (interceptContext.isIgnored()) {
            return;
        }
        super.currentTracerSpan(httpRequest, interceptContext);
    }

    @Override
    public void after(InterceptContext interceptContext) {

        if (interceptContext.isIgnored()) {
            return;
        }
        //TODO reactor 方式的处理逻辑？
        super.after(interceptContext);
    }
}
