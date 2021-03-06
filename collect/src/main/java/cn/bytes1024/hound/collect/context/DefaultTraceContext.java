package cn.bytes1024.hound.collect.context;

import cn.bytes1024.hound.commons.util.NamedThreadLocal;
import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.filter.TraceContextFilterOption;
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
public class DefaultTraceContext extends SofaTracerThreadLocalTraceContext implements TraceContext, TraceContextFilterOption {


    private SofaTracer sofaTracer;

    /**
     * 当前线程中活跃的span信息
     *
     * @author 江浩
     */
    private final NamedThreadLocal<ActiveTracerSpan> activeTracerSpans = new NamedThreadLocal<>("activeTracerSpans");

    private TraceContextFilterOption traceContextFilterOption;

    public DefaultTraceContext(SofaTracer sofaTracer, TraceContextFilterOption traceContextFilterOption) {
        this.sofaTracer = sofaTracer;
        this.traceContextFilterOption = traceContextFilterOption;
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

        this.filterOption(sofaTracerSpanContext, interceptContext);

        sofaTracerSpanContext.addBizBaggage(interceptContext.getProps());
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


    @Override
    public void filterOption(SofaTracerSpanContext sofaTracerSpanContext, InterceptContext interceptContext) {
        this.traceContextFilterOption.filterOption(sofaTracerSpanContext, interceptContext);
    }
}
