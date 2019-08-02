package cn.bytes1024.hound.plugins.druid;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.druid.interceptor.DruidPluginInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * druid 数据源
 *
 * @author 江浩
 */
public class DruidPooledConnectionPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {
        defineBuilder.pointName("plugin-druid-pooledConnection")
                .pointClass(named("com.alibaba.druid.pool.DruidPooledConnection"))
                .pointMethod(named("close"), DruidPluginInterceptor.class);
    }
}
