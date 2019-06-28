package cn.bytes1024.hound.collect.context;

import cn.bytes1024.hound.commons.util.NamedThreadLocal;
import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import com.alibaba.fastjson.JSONObject;
import com.alipay.common.tracer.core.SofaTracer;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.alipay.common.tracer.core.context.trace.SofaTracerThreadLocalTraceContext;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.EmptyStackException;
import java.util.Objects;

/**
 * 默认的追踪上线文
 *
 * @author 江浩
 */
@Getter
@Slf4j
public class DefaultTraceContext extends SofaTracerThreadLocalTraceContext implements TraceContext {


    private SofaTracer sofaTracer;

    /**
     * 当前线程中活跃的span信息
     *
     * @author 江浩
     */
    private final NamedThreadLocal<ActiveTracerSpan> activeTracerSpans = new NamedThreadLocal<>("activeTracerSpans");

    public DefaultTraceContext(SofaTracer sofaTracer) {
        this.sofaTracer = sofaTracer;
    }

    @Override
    public SofaTracer getSofaTracer() {
        return this.sofaTracer;
    }


    @Override
    public SofaTracerSpan stopCurrentTracerSpan(InterceptContext interceptContext) {
        SofaTracerSpan sofaTracerSpan = this.pop();
        if (Objects.isNull(sofaTracerSpan)) {
            return null;
        }

        SofaTracerSpanContext sofaTracerSpanContext = sofaTracerSpan.getSofaTracerSpanContext();
        //options setting
        //TODO 这很容易出问题

//        System.out.println("----------------------------------->>>>args<<<<-----------------------------------");
//        System.out.println(interceptContext.getArgs());
//        System.out.println("----------------------------------->>>>args<<<<-----------------------------------");

        sofaTracerSpanContext.setSysBaggageItem("TARGET_CLASS", interceptContext.getTarget().getClass().getName());
        sofaTracerSpanContext.setSysBaggageItem("METHOD_CLASS", interceptContext.getMethod().getName());
        sofaTracerSpanContext.setSysBaggageItem("INVOKER_RESULT", JSONObject.toJSONString(interceptContext.getResult()));

        sofaTracerSpan.finish();

        return sofaTracerSpan;
    }


    @Override
    public void push(SofaTracerSpan span) {
        if (span == null) {
            return;
        }
        //当前线程
        ActiveTracerSpan activeTracerSpan = activeTracerSpans.get();
        if (Objects.isNull(activeTracerSpan)) {
            activeTracerSpan = new DefaultActiveTracerSpan();
            activeTracerSpans.set(activeTracerSpan);
        }
        activeTracerSpan.addFirst(span);
    }

    @Override
    public SofaTracerSpan getCurrentSpan() throws EmptyStackException {
        if (this.isEmpty()) {
            return null;
        }

        ActiveTracerSpan activeTracerSpan = activeTracerSpans.get();
        if (Objects.isNull(activeTracerSpan)) {
            return null;
        }
        return activeTracerSpan.peekFirst();
    }

    @Override
    public SofaTracerSpan pop() throws EmptyStackException {
        if (this.isEmpty()) {
            return null;
        }
        ActiveTracerSpan activeTracerSpan = activeTracerSpans.get();
        if (Objects.isNull(activeTracerSpan)) {
            return null;
        }

        SofaTracerSpan sofaTracerSpan = activeTracerSpan.pollFirst();
        if (activeTracerSpan.size() <= 0) {
            activeTracerSpans.remove();
        }
        return sofaTracerSpan;
    }

    @Override
    public int getThreadLocalSpanSize() {
        ActiveTracerSpan activeTracerSpan = activeTracerSpans.get();
        return activeTracerSpan == null ? 0 : activeTracerSpan.size();
    }

    @Override
    public boolean isEmpty() {
        ActiveTracerSpan activeTracerSpan = activeTracerSpans.get();
        return activeTracerSpan == null || activeTracerSpan.size() <= 0;
    }

    @Override
    public void clear() {
        activeTracerSpans.remove();
    }


}
