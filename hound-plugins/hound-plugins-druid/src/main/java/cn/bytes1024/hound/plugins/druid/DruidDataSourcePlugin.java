package cn.bytes1024.hound.plugins.druid;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.druid.interceptor.DruidPluginInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;

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
                                .or(named("getConnection")
                                        .and(takesArgument(0, String.class))
                                        .and(takesArgument(1, String.class)))
                        , DruidPluginInterceptor.class);
    }
}
