package cn.bytes1024.hound.plugins.okhttp.interceptor;


import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.IgnoreInterceptor;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;
import cn.bytes1024.hound.plugins.okhttp.Containts;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * ok http 拦截器
 *
 * @author 江浩
 */
public class OkHttpRealCallMethodInterceptor extends AbstractMethodAroundInterceptor implements IgnoreInterceptor<Request> {

    public OkHttpRealCallMethodInterceptor(TraceContext traceContext) {
        super(traceContext);
    }

    @Override
    public void after(InterceptContext interceptContext) {
        Object result = interceptContext.getResult();
        super.after(interceptContext);
    }


    @Override
    public void before(InterceptContext interceptContext) {

        try {
            Field field = interceptContext.getTarget().getClass().getDeclaredField("originalRequest");
            field.setAccessible(true);
            if (ignore((Request) field.get(interceptContext.getTarget()))) {
                interceptContext.setIgnored(true);
                return;
            }
            super.before(interceptContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean ignore(Request handler) {
        return StringUtils.isNotBlank(handler.header(Containts.IGNORE_HEADER_KEY));
    }
}
