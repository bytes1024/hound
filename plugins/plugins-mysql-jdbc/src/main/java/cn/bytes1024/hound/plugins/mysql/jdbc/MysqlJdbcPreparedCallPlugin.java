//package cn.bytes1024.hound.plugins.mysql.jdbc;
//
//import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
//import cn.bytes1024.hound.plugins.mysql.jdbc.interceptor.MysqlJdbcPreparedCallInterceptor;
//
//import static net.bytebuddy.matcher.ElementMatchers.named;
//import static net.bytebuddy.matcher.ElementMatchers.takesArguments;
//
///**
// * mysql jdbc 插件
// *
// * @author 江浩
// */
//public class MysqlJdbcPreparedCallPlugin extends AbstractPluginDefine {
//    @Override
//    public void init(PluginDefineBuilder defineBuilder) {
//        defineBuilder.pointName(this.format("mysql-jdbc-preparedCall"))
//                .pointClass(
//                        named("com.mysql.jdbc.CallableStatement")
//                                .or(named("com.mysql.cj.jdbc.CallableStatement"))
//                )
//                .pointMethod(
//                        named("execute")
//                                .or(named("executeQuery"))
//                                .or(named("executeUpdate"))
//                                .or(named("registerOutParameter")
//                                        .and(takesArguments(int.class, int.class))
//                                )
//                                .or(named("registerOutParameter")
//                                        .and(takesArguments(int.class, int.class, int.class))
//                                )
//                                .or(named("registerOutParameter")
//                                        .and(takesArguments(int.class, int.class, String.class))
//                                )
//                        , MysqlJdbcPreparedCallInterceptor.class);
//    }
//}
