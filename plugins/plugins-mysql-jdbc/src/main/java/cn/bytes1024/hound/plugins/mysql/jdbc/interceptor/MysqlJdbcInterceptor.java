package cn.bytes1024.hound.plugins.mysql.jdbc.interceptor;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;

public class MysqlJdbcInterceptor extends AbstractMethodAroundInterceptor {

    public MysqlJdbcInterceptor(TraceContext traceContext) {
        super(traceContext);
    }


    @Override
    public void before(InterceptContext interceptContext) {
        super.before(interceptContext);

        System.out.println(interceptContext.getMethod() + "==============>>");
    }
}
