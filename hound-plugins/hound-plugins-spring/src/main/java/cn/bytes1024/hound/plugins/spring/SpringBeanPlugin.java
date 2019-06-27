package cn.bytes1024.hound.plugins.spring;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.spring.interceptor.SpringPluginMethodInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * spring bean 相关插件
 *
 * @author 江浩
 */
public class SpringBeanPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {


        defineBuilder
                .pointName("plugin-springBean")
                //@Service
                .pointClass(isAnnotatedWith(named(Service.class.getName()))
                        //@Component
                        .or(isAnnotatedWith(named(Component.class.getName())))
                        //@Repository
                        .or(isAnnotatedWith(named(Repository.class.getName())))
                        .or(isAnnotatedWith(named(Bean.class.getName())))
                )
                .pointMethod(any(),
                        SpringPluginMethodInterceptor.class);
    }
}
