//package cn.bytes1024.hound.plugins.mybatis;
//
//import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
//import cn.bytes1024.hound.plugins.mybatis.interceptor.MybatisPluginInterceptor;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
//
//import static net.bytebuddy.matcher.ElementMatchers.*;
//
///**
// * mybatis 插件
// * 注解定义的是接口需要找到接口指定的入口 TODO
// * @author 江浩
// */
//public class MybatisAnnotationMethodPlugin extends AbstractPluginDefine {
//    @Override
//    public void init(PluginDefineBuilder defineBuilder) {
//        defineBuilder.pointName("plugin-mybatis-annotation")
//                .pointClass(any())
//                .pointMethod(isAnnotatedWith(named(Select.class.getName()))
//                                .or(isAnnotatedWith(named(Update.class.getName())))
//                                .or(isAnnotatedWith(named(Delete.class.getName())))
//                        , MybatisPluginInterceptor.class);
//    }
//}
