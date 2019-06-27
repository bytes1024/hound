package cn.bytes1024.hound.plugins.okhttp.interceptor;


import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

/**
 * ok http 拦截器
 *
 * @author 江浩
 */
public class OkHttpRealCallMethodInterceptor extends AbstractMethodAroundInterceptor {

    public OkHttpRealCallMethodInterceptor(TraceContext traceContext) {
        super(traceContext);
    }

    @Override
    public void after(InterceptContext interceptContext) {
        Object result = interceptContext.getResult();
        super.after(interceptContext);
    }
}
