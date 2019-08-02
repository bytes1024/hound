package cn.bytes1024.hound.plugins.spring;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.spring.interceptor.SpringPluginMethodInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * spring mvc controller 相关注解插件
 *
 * @author 江浩
 */
public class SpringWebMvcPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {
        defineBuilder.pointName("plugin-springWebmvc")
                .pointClass(isAnnotatedWith(named(Controller.class.getName()))
                        .or(isAnnotatedWith(named(RestController.class.getName()))))
                .pointMethod(isAnnotatedWith(named(GetMapping.class.getName()))
                        , SpringPluginMethodInterceptor.class)
                .pointMethod(
                        isAnnotatedWith(named(PostMapping.class.getName()))
                        , SpringPluginMethodInterceptor.class)
                .pointMethod(
                        isAnnotatedWith(named(PutMapping.class.getName()))
                        , SpringPluginMethodInterceptor.class)
                .pointMethod(
                        isAnnotatedWith(named(DeleteMapping.class.getName()))
                        , SpringPluginMethodInterceptor.class)
                .pointMethod(
                        isAnnotatedWith(named(PatchMapping.class.getName()))
                        , SpringPluginMethodInterceptor.class);
    }
}
