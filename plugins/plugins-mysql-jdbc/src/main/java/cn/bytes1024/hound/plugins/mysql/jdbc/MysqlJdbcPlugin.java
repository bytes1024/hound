package cn.bytes1024.hound.plugins.mysql.jdbc;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.mysql.jdbc.interceptor.MysqlJdbcInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * mysql 插件
 *
 * @author 江浩
 */
public class MysqlJdbcPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {

        defineBuilder.pointName(super.format("mysql"))
                .pointClass(named("com.mysql.cj.jdbc.PreparedStatement"))
                .pointMethod(named("execute")
                        .or(named("executeQuery"))
                        .or(named("executeUpdate"))
                        .or(named("executeLargeUpdate")), MysqlJdbcInterceptor.class);
    }
}
