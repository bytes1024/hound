package cn.bytes1024.hound.plugins.define;

import cn.bytes1024.hound.plugins.define.interceptor.ConstructorInterceptor;
import cn.bytes1024.hound.plugins.define.interceptor.MethodAroundInterceptor;
import cn.bytes1024.hound.plugins.define.interceptor.StaticMethodAroundInterceptor;
import lombok.Getter;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 江浩
 */
public abstract class AbstractPluginDefine implements PluginDefine {


    private PluginDefineBuilder defineBuilder;

    public AbstractPluginDefine() {
        this.defineBuilder = new PluginDefineBuilder();
        this.init(this.defineBuilder);
    }

    /**
     * 初始方法
     *
     * @param defineBuilder
     */
    public abstract void init(PluginDefineBuilder defineBuilder);


    @Override
    public ElementMatcher<? super TypeDescription> classDescription() {
        return defineBuilder.getClassDescription();
    }


    @Override
    public List<EnhanceContext> enhanceContexts() {
        return defineBuilder.getInterceptorBuilder().getEnhanceContexts();
    }


    @Override
    public String name() {
        final String name = defineBuilder.getName();
        return StringUtils.isBlank(name) ? UUID.randomUUID().toString() : name;
    }


    @Getter
    public static class PluginDefineBuilder {

        private ElementMatcher<? super TypeDescription> classDescription;

        private String name;

        private InterceptorBuilder interceptorBuilder;

        public PluginDefineBuilder pointName(String name) {
            this.name = name;
            return this;
        }

        public InterceptorBuilder pointClass(ElementMatcher<? super TypeDescription> classDescription) {
            this.classDescription = classDescription;
            return interceptorBuilder = new InterceptorBuilder(this.name);
        }
    }


    @Getter
    public static class InterceptorBuilder {

        private List<EnhanceContext> enhanceContexts = new ArrayList<>();
        private String pluginName;

        private InterceptorBuilder() {
        }

        public InterceptorBuilder(String pluginName) {
            this.pluginName = pluginName;
        }

        public <R extends MethodAroundInterceptor> InterceptorBuilder pointMethod(ElementMatcher<? super MethodDescription> methodDescription, Class<R> interceptorClass) {
            pointSetting(methodDescription, interceptorClass.getName());
            return this;
        }

        public <R extends StaticMethodAroundInterceptor> InterceptorBuilder pointStaticMethod(ElementMatcher<? super MethodDescription> methodDescription, Class<R> interceptorClass) {
            pointSetting(methodDescription, interceptorClass.getName());
            return this;
        }

        public <R extends ConstructorInterceptor> InterceptorBuilder pointConstructor(ElementMatcher<? super MethodDescription> methodDescription, Class<R> interceptorClass) {
            pointSetting(methodDescription, interceptorClass.getName());
            return this;
        }

        private void pointSetting(ElementMatcher<? super MethodDescription> methodDescription, String interceptorClassName) {

            if (Objects.isNull(methodDescription) || StringUtils.isBlank(interceptorClassName)) {
                return;
            }

            EnhanceContext enhanceContext = new EnhanceContext();
            enhanceContext.setMethodDescription(methodDescription);
            enhanceContext.setInterceptorClassName(interceptorClassName);
            enhanceContext.setRefPluginName(this.pluginName);

            binder(enhanceContext);
        }

        private void binder(EnhanceContext enhanceContext) {
            if (!enhanceContexts.contains(enhanceContext)) {
                enhanceContexts.add(enhanceContext);
            }
        }

    }


}
