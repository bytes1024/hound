package cn.bytes1024.hound.plugins.webflux.interceptor;


import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

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
