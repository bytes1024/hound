package com.bytes.hound.plugins.webflux.interceptor;

import com.bytes.hound.plugins.define.InterceptContext;
import com.bytes.hound.plugins.define.TraceContext;
import com.bytes.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

public class OnOutboundCompleteMethodInterceptor extends AbstractMethodAroundInterceptor {
    public OnOutboundCompleteMethodInterceptor(TraceContext traceContext) {
        super(traceContext);
    }

    @Override
    public void before(InterceptContext interceptContext) {
        System.out.println("onOutboundComplete..........");
    }

    @Override
    public void after(InterceptContext interceptContext) {
        System.out.println("onOutboundComplete after....");
    }
}
