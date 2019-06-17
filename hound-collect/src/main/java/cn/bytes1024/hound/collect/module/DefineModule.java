package cn.bytes1024.hound.collect.module;

import cn.bytes1024.hound.collect.agent.AgentOption;
import cn.bytes1024.hound.collect.context.ApplicationContext;
import cn.bytes1024.hound.collect.enhance.EnhanceFactory;
import cn.bytes1024.hound.collect.enhance.InterceptorFactory;
import cn.bytes1024.hound.collect.enhance.rule.*;
import cn.bytes1024.hound.collect.handler.CollectAgentHandler;
import cn.bytes1024.hound.collect.handler.Handler;
import cn.bytes1024.hound.collect.module.providers.*;
import cn.bytes1024.hound.plugins.define.TraceContext;
import com.alipay.common.tracer.core.SofaTracer;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import net.bytebuddy.agent.builder.AgentBuilder;

/**
 * 初始定义的模块信息
 *
 * @author 江浩
 */
public class DefineModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApplicationContext.class).toProvider(ApplicationContextProvider.class).in(Scopes.SINGLETON);
        bind(AgentOption.class).toProvider(AgentOptionProvider.class).in(Scopes.SINGLETON);

        bind(AgentBuilder.class).to(AgentBuilder.Default.class).in(Scopes.SINGLETON);
        bind(Handler.class).annotatedWith(Names.named("agent")).to(CollectAgentHandler.class).in(Scopes.SINGLETON);

        bind(Reporter.class).toProvider(ServerReporterProvider.class).in(Scopes.SINGLETON);
        bind(SofaTracer.class).toProvider(SofaTracerProvider.class).in(Scopes.SINGLETON);

        bind(TraceContext.class).toProvider(TraceContextProvider.class).in(Scopes.SINGLETON);
        bind(InterceptorFactory.class).toProvider(InterceptorFactoryProvider.class).in(Scopes.SINGLETON);
        bind(EnhanceFactory.class).toProvider(EnhanceFactoryProvider.class).in(Scopes.SINGLETON);

        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.METHOD)).to(EnhanceMethodRule.class).in(Scopes.SINGLETON);
        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.CONSTRUCTOR)).to(EnhanceConstructorRule.class).in(Scopes.SINGLETON);
        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.STATIC_METHOD)).to(EnhanceStaticMethodRule.class).in(Scopes.SINGLETON);
        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.SOURCE)).to(EnhanceSourceRule.class).in(Scopes.SINGLETON);

    }
}
