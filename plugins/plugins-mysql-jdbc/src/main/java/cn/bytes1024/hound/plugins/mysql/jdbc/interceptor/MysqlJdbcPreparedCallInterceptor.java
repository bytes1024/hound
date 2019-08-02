//package cn.bytes1024.hound.plugins.mysql.jdbc.interceptor;
//
//import cn.bytes1024.hound.plugins.define.InterceptContext;
//import cn.bytes1024.hound.plugins.define.TraceContext;
//import cn.bytes1024.hound.plugins.define.interceptor.supper.AbstractMethodAroundInterceptor;
//
///**
// * preared call
// *
// * @author 江浩
// */
//public class MysqlJdbcPreparedCallInterceptor extends AbstractMethodAroundInterceptor {
//    public MysqlJdbcPreparedCallInterceptor(TraceContext traceContext) {
//        super(traceContext);
//    }
//
//    @Override
//    public void before(InterceptContext interceptContext) {
//
//        Object[] objects = interceptContext.getArgs();
//        if (objects != null) {
//            for (Object object : objects) {
//                System.out.println(object);
//            }
//        }
//
//
//        super.before(interceptContext);
//    }
//}
