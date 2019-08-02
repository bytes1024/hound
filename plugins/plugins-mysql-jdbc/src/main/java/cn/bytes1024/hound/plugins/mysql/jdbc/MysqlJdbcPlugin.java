package cn.bytes1024.hound.plugins.mysql.jdbc;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.mysql.jdbc.interceptor.MysqlJdbcInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * mysql 插件
 *
 * @author 江浩
 */
public class MysqlJdbcPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {

        defineBuilder.pointName(this.format("mysql-jdbc"))
                .pointClass(named("com.mysql.jdbc.Connection")
                        .or(named("com.mysql.jdbc.ConnectionImpl"))
                        .or(named("com.mysql.cj.jdbc.ConnectionImpl"))
                )
                .pointMethod(
                        named("prepareStatement").and(takesArguments(String.class))
                                .or(named("prepareStatement").and(takesArguments(String.class, int.class)))
                                .or(named("prepareStatement").and(takesArguments(String.class, int.class)))
                                .or(named("prepareStatement").and(takesArguments(String.class, int[].class)))
                                .or(named("prepareStatement").and(takesArguments(String.class, String[].class)))
                                .or(named("prepareStatement").and(takesArguments(int.class, int.class)))
                                .or(named("prepareStatement").and(takesArguments(int.class, int.class, int.class)))
                        , MysqlJdbcInterceptor.class)

                .pointMethod(
                        named("prepareCall")/*.and(takesArguments(String.class))*/
                                .or(named("prepareCall")/*.and(takesArguments(String.class, int.class, int.class))*/)
                                .or(named("prepareCall")/*.and(takesArguments(String.class, int.class, int.class, int.class))*/)
                        , MysqlJdbcInterceptor.class)

                .pointMethod(
                        named("setAutoCommit")/*.and(takesArguments(Boolean.class))*/
                        , MysqlJdbcInterceptor.class)

                .pointMethod(named("commit"), MysqlJdbcInterceptor.class)

                .pointMethod(named("rollback"), MysqlJdbcInterceptor.class);


    }
}
