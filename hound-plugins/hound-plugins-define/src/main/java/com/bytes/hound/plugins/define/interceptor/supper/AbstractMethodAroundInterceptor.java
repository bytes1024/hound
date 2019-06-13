package com.bytes.hound.plugins.define.interceptor.supper;

import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.bytes.hound.plugins.define.InterceptContext;
import com.bytes.hound.plugins.define.TraceContext;
import com.bytes.hound.plugins.define.interceptor.InterceptorPluginAware;
import com.bytes.hound.plugins.define.interceptor.MethodAroundInterceptor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static io.opentracing.tag.Tags.SPAN_KIND;
import static io.opentracing.tag.Tags.SPAN_KIND_SERVER;

/**
 * 方法拦截器
 *
 * @author 江浩
 */
@Getter
@Slf4j
public abstract class AbstractMethodAroundInterceptor implements MethodAroundInterceptor, InterceptorPluginAware {

    public static final String TRANSMISSION_KEY = "TRANSMISSION_KEY";

    private TraceContext traceContext;

    private String pluginName;


    public AbstractMethodAroundInterceptor(TraceContext traceContext) {
        this.traceContext = traceContext;
    }


    @Override
    public void defineName(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    public void before(InterceptContext interceptContext) {
        try {
            SofaTracerSpan sofaTracerSpan = traceContext.getCurrentSpan();
            if (Objects.isNull(sofaTracerSpan)) {
                log.info("current span is empty ! {} \n\t\t{}\t{}", Thread.currentThread().getId()
                        , interceptContext.getTarget().getClass(), interceptContext.getMethod().getName());
                return;
            }
            this.currentTracerSpan(sofaTracerSpan, interceptContext);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * 构建当前的span信息
     *
     * @param preSofaTracerSpan :  上一个span信息
     * @param interceptContext  :   构建当前span请求信息
     * @return : com.alipay.common.tracer.core.span.SofaTracerSpan
     * @author 江浩
     */
    public SofaTracerSpan currentTracerSpan(SofaTracerSpan preSofaTracerSpan, InterceptContext interceptContext) {
        SofaTracerSpan thatSofaTracerSpan = (SofaTracerSpan) getTraceContext()
                .getSofaTracer()
                .buildSpan(StringUtils.isBlank(pluginName) ? this.getClass().getName() : pluginName)
                .withTag(SPAN_KIND.getKey(), SPAN_KIND_SERVER)
                .asChildOf(preSofaTracerSpan)
                .start();

        getTraceContext().push(thatSofaTracerSpan);
        return thatSofaTracerSpan;
    }


    @Override
    public void after(InterceptContext interceptContext) {
        getTraceContext().stopCurrentTracerSpan(interceptContext);
    }


}
