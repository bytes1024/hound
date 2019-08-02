//package cn.bytes1024.hound.plugins.mysql.jdbc;
//
//import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
//import cn.bytes1024.hound.plugins.mysql.jdbc.interceptor.MysqlJdbcPreparedStatementInterceptor;
//
//import static net.bytebuddy.matcher.ElementMatchers.named;
//
///**
// * mysql jdbc 插件
// *
// * @author 江浩
// */
//public class MysqlJdbcPreparedStatementPlugin extends AbstractPluginDefine {
//    @Override
//    public void init(PluginDefineBuilder defineBuilder) {
//        defineBuilder.pointName(this.format("mysql-jdbc-preparedStatement"))
//                .pointClass(
//                        named("com.mysql.jdbc.PreparedStatement")
//                                .or(named("com.mysql.cj.jdbc.PreparedStatement"))
//                                .or(named("com.mysql.cj.jdbc.ClientPreparedStatement"))
//                                .or(named("com.mysql.cj.jdbc.ServerPreparedStatement"))
//                )
//                .pointMethod(named("execute")
//                                .or(named("executeQuery"))
//                                .or(named("executeUpdate"))
//                        , MysqlJdbcPreparedStatementInterceptor.class)
//
//        ;
//    }
//}
