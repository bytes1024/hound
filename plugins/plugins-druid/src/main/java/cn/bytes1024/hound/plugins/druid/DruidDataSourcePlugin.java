package cn.bytes1024.hound.plugins.druid;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.druid.interceptor.DruidPluginInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * druid 数据源
 *
 * @author 江浩
 */
public class DruidDataSourcePlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {
        defineBuilder.pointName("plugin-druid-dataSource")
                .pointClass(named("com.alibaba.druid.pool.DruidDataSource"))
                .pointMethod(named("close")
                                .or(named("getPooledConnection")
                                        .or(named("getConnection").and(takesArguments(1)))
                                )
                        , DruidPluginInterceptor.class);
    }
}
