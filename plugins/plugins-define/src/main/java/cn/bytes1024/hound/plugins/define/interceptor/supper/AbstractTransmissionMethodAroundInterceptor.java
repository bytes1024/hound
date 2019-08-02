package cn.bytes1024.hound.plugins.define.interceptor.supper;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import com.alibaba.fastjson.JSONObject;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Objects;

import static io.opentracing.tag.Tags.SPAN_KIND;
import static io.opentracing.tag.Tags.SPAN_KIND_SERVER;

/**
 * 远程传递操作方法
 * <p></p>
 *
 * @author 江浩
 */
@Getter
@Slf4j
public abstract class AbstractTransmissionMethodAroundInterceptor<H> extends AbstractMethodAroundInterceptor {

    public AbstractTransmissionMethodAroundInterceptor(TraceContext traceContext) {
        super(traceContext);
    }

    private H remoteHandle;

    private RemoteTransmission<H> remoteTransmission;

    public void setRemoteTransmission(RemoteTransmission<H> remoteTransmission) {
        this.remoteTransmission = remoteTransmission;
    }

    @Override
    public void before(InterceptContext interceptContext) {
        invoker(interceptContext);
    }


    public void before(@NonNull H handler, @NonNull InterceptContext interceptContext) {
        //setRemoteHandle(handler);
        this.remoteHandle = handler;
        invoker(interceptContext);
    }

    private void invoker(InterceptContext interceptContext) {
        SofaTracerSpan sofaTracerSpan = getTraceContext().getCurrentSpan();
        if (!Objects.isNull(sofaTracerSpan)) {
            SofaTracerSpanContext sofaTracerSpanContext = sofaTracerSpan.getSofaTracerSpanContext();
            remoteTransmission.transmission(remoteHandle, TRANSMISSION_KEY, sofaTracerSpanContext);
        }
        //before
        super.before(interceptContext);
    }


    /**
     * 构建当前span信息
     *
     * @param handler          :
     * @param interceptContext :
     * @return : void
     * @author 江浩
     */
    protected void currentTracerSpan(H handler, InterceptContext interceptContext) {
        try {
            SofaTracerSpanContext sofaTracerSpanContext = getSpanContextFrom(handler);
            this.currentTracerSpan(sofaTracerSpanContext, interceptContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据context 构建span信息
     *
     * @param sofaTracerSpanContext :
     * @param interceptContext      :
     * @return : void
     * @author 江浩
     */
    public void currentTracerSpan(SofaTracerSpanContext sofaTracerSpanContext, InterceptContext interceptContext) {
        SofaTracerSpan currentTracerSpan = new SofaTracerSpan(getTraceContext().getSofaTracer(),
                System.currentTimeMillis(), getPluginName(), sofaTracerSpanContext
                , new HashMap<String, String>(5) {
            {
                put(SPAN_KIND.getKey(), SPAN_KIND_SERVER);
            }
        });
        super.currentTracerSpan(currentTracerSpan, interceptContext);
    }

    /**
     * 转换context
     *
     * @param spanContextJson :
     * @return : com.alipay.common.tracer.core.context.span.SofaTracerSpanContext
     * @author 江浩
     */
    public SofaTracerSpanContext convert(String spanContextJson) {
        if (StringUtils.isBlank(spanContextJson)) {
            return null;
        }
        try {
            return JSONObject.parseObject(spanContextJson, SofaTracerSpanContext.class);
        } catch (Exception e) {
            return null;
        }
    }


    public String convert(SofaTracerSpanContext sofaTracerSpanContext) {
        try {
            return JSONObject.toJSONString(sofaTracerSpanContext);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取信息
     *
     * @param handler :
     * @return : com.alipay.common.tracer.core.context.span.SofaTracerSpanContext
     * @author 江浩
     */
    private SofaTracerSpanContext getSpanContextFrom(H handler) {
        return this.remoteTransmission.receive(handler, TRANSMISSION_KEY);
    }
}
