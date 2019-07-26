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
                        named("selectOne").and(takesArguments(2))
                                .or(named("selectList").and(takesArguments(3)))
                                .or(named("selectMap").and(takesArguments(4)))
                                .or(named("select").and(takesArguments(3)))
                                .or(named("insert"))
                                .or(named("update"))
                                .or(named("delete")
                                )
                        , MybatisPluginInterceptor.class);

    }
}
