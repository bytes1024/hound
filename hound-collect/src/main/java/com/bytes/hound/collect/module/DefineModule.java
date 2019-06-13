package com.bytes.hound.collect.module;

import com.alipay.common.tracer.core.SofaTracer;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.bytes.hound.collect.agent.AgentOption;
import com.bytes.hound.collect.context.ApplicationContext;
import com.bytes.hound.collect.enhance.EnhanceFactory;
import com.bytes.hound.collect.enhance.InterceptorFactory;
import com.bytes.hound.collect.enhance.rule.*;
import com.bytes.hound.collect.handler.CollectAgentHandler;
import com.bytes.hound.collect.handler.Handler;
import com.bytes.hound.collect.module.providers.*;
import com.bytes.hound.plugins.define.TraceContext;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import net.bytebuddy.agent.builder.AgentBuilder;

import static com.google.inject.name.Names.named;

/**
 * 初始定义的模块信息
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
