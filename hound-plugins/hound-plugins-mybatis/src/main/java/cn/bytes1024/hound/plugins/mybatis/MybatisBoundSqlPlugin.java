package cn.bytes1024.hound.plugins.mybatis;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.mybatis.interceptor.MybatisBoundSqlPluginInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * 执行sql语句
 *
 * @author 江浩
 */
public class MybatisBoundSqlPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {
        defineBuilder.pointName("plugin-mybatis-boundSql")
                .pointClass(named("org.apache.ibatis.mapping.MappedStatement"))
                .pointMethod(
                        named("getBoundSql")
                        , MybatisBoundSqlPluginInterceptor.class);
    }
}
