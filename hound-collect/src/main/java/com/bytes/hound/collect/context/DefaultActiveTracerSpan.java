package com.bytes.hound.collect.context;

import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 线程默认活跃span 的实现
 *
 * @author 江浩
 */
public class DefaultActiveTracerSpan implements ActiveTracerSpan {

    private LinkedList<SofaTracerSpan> tracerSpans = Lists.newLinkedList();


    @Override
    public void addFirst(SofaTracerSpan sofaTracerSpan) {
        if (!Objects.isNull(sofaTracerSpan)) {
            tracerSpans.addFirst(sofaTracerSpan);
        }
    }

    @Override
    public Integer size() {
        return tracerSpans.size();
    }

    @Override
    public SofaTracerSpan peekFirst() {
        return tracerSpans.getFirst();
    }

    @Override
    public SofaTracerSpan pollFirst() {
        return tracerSpans.removeFirst();
    }

}
