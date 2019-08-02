package cn.bytes1024.hound.plugins.httpclient.interceptor;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractTransmissionMethodAroundInterceptor;
import cn.bytes1024.hound.plugins.define.interceptor.supper.RemoteTransmission;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import org.apache.http.HttpRequest;

/**
 * httpclient 插件
 * <p>
 * 需要远程传递
 * </p>
 *
 * @author 江浩
 */
public class HttpClientMethodInterceptor extends AbstractTransmissionMethodAroundInterceptor {
    public HttpClientMethodInterceptor(TraceContext traceContext) {
        super(traceContext);
        setRemoteTransmission(new RemoteTransmission<HttpRequest>() {
            @Override
            public void transmission(HttpRequest handler, String key, SofaTracerSpanContext spanContext) {
                handler.setHeader(key, convert(spanContext));
            }
        });
    }


    @Override
    public void before(InterceptContext interceptContext) {
        HttpRequest httpRequest = getHttpRequest(interceptContext.getArgs());
        super.before(httpRequest, interceptContext);
    }

    private HttpRequest getHttpRequest(Object[] args) {
        if (args != null && args.length >= 1 && args[0] != null && args[0] instanceof HttpRequest) {
            return (HttpRequest) args[0];
        }
        return null;
    }
}
