package com.bytes.hound.plugins.okhttp.interceptor;

import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.bytes.hound.plugins.define.InterceptContext;
import com.bytes.hound.plugins.define.TraceContext;
import com.bytes.hound.plugins.define.interceptor.supper.AbstractTransmissionMethodAroundInterceptor;
import com.bytes.hound.plugins.define.interceptor.supper.RemoteTransmission;
import okhttp3.Headers;
import okhttp3.Request;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ok http 拦截器
 *
 * @author 江浩
 */
public class OkHttpClientMethodInterceptor extends AbstractTransmissionMethodAroundInterceptor<Request> {
    public OkHttpClientMethodInterceptor(TraceContext traceContext) {
        super(traceContext);

        setRemoteTransmission(new RemoteTransmission<Request>() {
            @Override
            public void transmission(Request handler, String key, SofaTracerSpanContext spanContext) {
                try {
                    Field headersField = Request.class.getDeclaredField("headers");
                    headersField.setAccessible(true);
                    Headers.Builder headerBuilder = handler.headers().newBuilder();
                    //原始header信息
                    Headers headers = handler.headers();

                    if (!Objects.isNull(headers)) {
                        Map<String, List<String>> multimap = headers.toMultimap();
                        if (MapUtils.isNotEmpty(multimap)) {
                            for (Map.Entry<String, List<String>> headerEntry : multimap.entrySet()) {
                                headerBuilder.add(headerEntry.getKey(), StringUtils.join(headerEntry.getValue()));
                            }
                        }
                    }

                    headerBuilder.add(key, convert(spanContext));
                    headersField.set(handler, headerBuilder.build());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void before(InterceptContext interceptContext) {
        Object[] args = interceptContext.getArgs();

        Object arg0 = args[0];
        if (Objects.isNull(arg0) || !(arg0 instanceof Request)) {
            interceptContext.setIgnored(true);
            return;
        }

        super.before((Request) arg0, interceptContext);
    }

    @Override
    public void after(InterceptContext interceptContext) {
        if (Objects.isNull(interceptContext) || interceptContext.isIgnored()) {
            return;
        }
        super.after(interceptContext);
    }
}
