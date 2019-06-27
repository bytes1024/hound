package cn.bytes1024.hound.plugins.tomcat;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.tomcat.interceptor.TomcatMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * tomcat 7x+ 插件支持
 *
 * @author 江浩
 */
public class Tomcat7xPlugin extends AbstractPluginDefine {
    @Override
    public void init(AbstractPluginDefine.PluginDefineBuilder defineBuilder) {

        defineBuilder.pointName("tomcat")
                .pointClass(named("org.apache.catalina.core.StandardHostValve"))
                .pointMethod(named("invoke")
                                .and(takesArguments(2))
                                .and(takesArgument(0, named("org.apache.catalina.connector.Request")))
                                .and(takesArgument(1, named("org.apache.catalina.connector.Response")))
                        , TomcatMethodInterceptor.class);

    }


}
