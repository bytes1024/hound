package cn.bytes1024.hound.collect.module;

import cn.bytes1024.hound.collect.context.ApplicationContext;
import cn.bytes1024.hound.collect.enhance.EnhanceRuleChainProxy;
import cn.bytes1024.hound.collect.enhance.InterceptorFactory;
import cn.bytes1024.hound.collect.enhance.rule.*;
import cn.bytes1024.hound.collect.module.providers.*;
import cn.bytes1024.hound.collect.processor.AgentCollectProcessor;
import cn.bytes1024.hound.collect.processor.MetricsCollectProcessor;
import cn.bytes1024.hound.collect.processor.Processor;
import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.plugins.define.TraceContext;
import cn.bytes1024.hound.plugins.define.filter.DefaultTraceContextFilterOption;
import cn.bytes1024.hound.plugins.define.filter.TraceContextFilterOption;
import cn.bytes1024.hound.transfers.define.buffer.TransferBuffer;
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


    public static class Alias {
        public static final String AGENT = "agent";
        public static final String METRICS = "metrics";
    }


    @Override
    protected void configure() {
        bind(ApplicationContext.class).toProvider(ApplicationContextProvider.class).in(Scopes.SINGLETON);
        bind(ConfigOption.class).toProvider(AgentOptionProvider.class).in(Scopes.SINGLETON);

        bind(AgentBuilder.class).to(AgentBuilder.Default.class).in(Scopes.SINGLETON);
        bind(Processor.class).annotatedWith(Names.named(Alias.AGENT))
                .to(AgentCollectProcessor.class).in(Scopes.SINGLETON);

        bind(Processor.class).annotatedWith(Names.named(Alias.METRICS))
                .to(MetricsCollectProcessor.class).in(Scopes.SINGLETON);

        bind(Reporter.class).toProvider(ServerReporterProvider.class).in(Scopes.SINGLETON);

        bind(TransferBuffer.class).toProvider(TransferBufferProvider.class).in(Scopes.SINGLETON);
        bind(SofaTracer.class).toProvider(SofaTracerProvider.class).in(Scopes.SINGLETON);

        bind(TraceContextFilterOption.class).to(DefaultTraceContextFilterOption.class).in(Scopes.SINGLETON);
        bind(TraceContext.class).toProvider(TraceContextProvider.class).in(Scopes.SINGLETON);
        bind(InterceptorFactory.class).toProvider(InterceptorFactoryProvider.class).in(Scopes.SINGLETON);
        bind(EnhanceRuleChainProxy.class).toProvider(EnhanceRuleChainProxyProvider.class).in(Scopes.SINGLETON);

        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.METHOD)).to(EnhanceMethodRule.class).in(Scopes.SINGLETON);
        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.CONSTRUCTOR)).to(EnhanceConstructorRule.class).in(Scopes.SINGLETON);
        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.STATIC_METHOD)).to(EnhanceStaticMethodRule.class).in(Scopes.SINGLETON);
        bind(EnhanceRule.class).annotatedWith(Names.named(EnhanceRule.Key.SOURCE)).to(EnhanceSourceRule.class).in(Scopes.SINGLETON);

    }
}
