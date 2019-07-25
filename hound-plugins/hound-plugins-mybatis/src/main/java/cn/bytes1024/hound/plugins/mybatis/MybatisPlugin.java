package cn.bytes1024.hound.plugins.mybatis;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.mybatis.interceptor.MybatisPluginInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * mybatis 插件
 *
 * @author 江浩
 */
public class MybatisPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {

        defineBuilder.pointName("plugin-mybatis")
                .pointClass(named("org.apache.ibatis.session.DefaultSqlSession"))
                .pointMethod(
                        named("select")
                                .or(named("update"))
                                .or(named("insert"))
                                .or(named("delete")), MybatisPluginInterceptor.class);


    }
}
