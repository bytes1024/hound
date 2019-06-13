package com.bytes.hound.plugins.tomcat.interceptor;

import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.bytes.hound.plugins.define.InterceptContext;
import com.bytes.hound.plugins.define.TraceContext;
import com.bytes.hound.plugins.define.interceptor.supper.AbstractTransmissionMethodAroundInterceptor;
import com.bytes.hound.plugins.define.interceptor.supper.RemoteTransmission;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * tomcat 方法拦截
 *
 * @author 江浩
 */
public class TomcatMethodInterceptor extends AbstractTransmissionMethodAroundInterceptor<HttpServletRequest> {

    static final String IGNORED_URL = ".*favicon.ico.*";

    public TomcatMethodInterceptor(TraceContext traceContext) {
        super(traceContext);

        setRemoteTransmission(new RemoteTransmission<HttpServletRequest>() {
            @Override
            public SofaTracerSpanContext receive(HttpServletRequest handler, String key) {
                String spanContext = handler.getHeader(key);
                return convert(spanContext);
            }
        });
    }

    @Override
    public void before(InterceptContext interceptContext) {
        Object[] args = interceptContext.getArgs();
        final Object request = args[0], response = args[1];
        final HttpServletRequest httpServletRequest = (request instanceof HttpServletRequest) ? (HttpServletRequest) request : null;
        interceptContext.setIgnored(Objects.isNull(httpServletRequest)
                || httpServletRequest.getRequestURL().toString().matches(IGNORED_URL)
        );
        if (interceptContext.isIgnored()) {
            return;
        }
        super.currentTracerSpan(httpServletRequest, interceptContext);

    }

    @Override
    public void after(InterceptContext interceptContext) {

        if (interceptContext.isIgnored()) {
            return;
        }

        super.after(interceptContext);
    }

}
