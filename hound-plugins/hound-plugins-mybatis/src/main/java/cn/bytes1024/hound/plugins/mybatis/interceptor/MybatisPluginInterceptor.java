package cn.bytes1024.hound.plugins.mybatis.interceptor;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

/**
 * mybatis 默认插件
 *
 * @author 江浩
 */
public class MybatisPluginInterceptor extends AbstractMethodAroundInterceptor {

    public MybatisPluginInterceptor(TraceContext traceContext) {
        super(traceContext);
    }


    @Override
    public void before(InterceptContext interceptContext) {
//
//        Object[] args = interceptContext.getArgs();
//        System.out.println(args[0]);
        super.before(interceptContext);
    }
}
