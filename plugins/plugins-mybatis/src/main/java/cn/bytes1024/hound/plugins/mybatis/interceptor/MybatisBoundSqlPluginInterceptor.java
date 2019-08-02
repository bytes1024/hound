package cn.bytes1024.hound.plugins.mybatis.interceptor;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;

import java.util.HashMap;

@Slf4j
public class MybatisBoundSqlPluginInterceptor extends AbstractMethodAroundInterceptor {
    public MybatisBoundSqlPluginInterceptor(TraceContext traceContext) {
        super(traceContext);
    }

    @Override
    public void after(InterceptContext interceptContext) {
        try {
            Object object = interceptContext.getResult();
            if (object instanceof BoundSql) {
                BoundSql boundSql = (BoundSql) object;
                interceptContext.setProps(new HashMap<String, String>() {{
                    putIfAbsent("sql", boundSql.getSql());
                }});
            }
        } catch (Exception e) {
        }

        super.after(interceptContext);
    }
}
