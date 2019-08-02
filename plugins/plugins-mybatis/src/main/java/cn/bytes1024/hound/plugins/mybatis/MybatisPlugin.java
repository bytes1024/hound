package cn.bytes1024.hound.plugins.mybatis;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.mybatis.interceptor.MybatisPluginInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * mybatis 插件
 *
 * @author 江浩
 */
public class MybatisPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {

        defineBuilder.pointName("plugin-mybatis")
                .pointClass(named("org.apache.ibatis.session.defaults.DefaultSqlSession")
                        .or(named("org.mybatis.spring.SqlSessionTemplate")
                        )
                )
                .pointMethod(
                        named("selectOne").and(takesArguments(2)), MybatisPluginInterceptor.class)
                .pointMethod(named("selectList").and(takesArguments(3)), MybatisPluginInterceptor.class)
                .pointMethod(named("selectMap").and(takesArguments(4)), MybatisPluginInterceptor.class)
                .pointMethod(named("select").and(takesArguments(3)), MybatisPluginInterceptor.class)
                .pointMethod(named("insert"), MybatisPluginInterceptor.class)
                .pointMethod(named("update"), MybatisPluginInterceptor.class)
                .pointMethod(named("delete"), MybatisPluginInterceptor.class);
    }
}
