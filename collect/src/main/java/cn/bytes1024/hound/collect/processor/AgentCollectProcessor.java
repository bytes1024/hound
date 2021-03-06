package cn.bytes1024.hound.collect.processor;

import cn.bytes1024.hound.collect.enhance.EnhanceRuleChainProxy;
import cn.bytes1024.hound.commons.enums.ProcessorStatus;
import cn.bytes1024.hound.commons.enums.ProcessorType;
import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.option.ConfigOptionDefine;
import cn.bytes1024.hound.loader.ExtensionLoader;
import cn.bytes1024.hound.plugins.define.EnhanceContext;
import cn.bytes1024.hound.plugins.define.PluginDefine;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

/**
 * @author 江浩
 */
@Slf4j
public class AgentCollectProcessor extends AbstractProcessor {

    private AgentBuilder agentBuilder;

    private Instrumentation instrumentation;

    private EnhanceRuleChainProxy enhanceRuleChainProxy;

    private ExtensionLoader<PluginDefine> extensionLoader = ExtensionLoader.getExtensionLoader(PluginDefine.class);

    private AtomicReference<ProcessorStatus> processorStatusReference = new AtomicReference<>(status());


    @Inject
    public AgentCollectProcessor(AgentBuilder agentBuilder,
                                 Instrumentation instrumentation,
                                 EnhanceRuleChainProxy enhanceRuleChainProxy) {
        this.agentBuilder = agentBuilder;
        this.instrumentation = instrumentation;
        this.enhanceRuleChainProxy = enhanceRuleChainProxy;
    }

    @Override
    public ProcessorType type() {
        return ProcessorType.AGENT;
    }

    @Override
    public void start(ConfigOption configOption) {

        //是否独立启动

        if (!ConfigOptionDefine.isEnabledAgent(configOption)) {
            if (log.isDebugEnabled()) {
                log.debug("processor [{}] enabled = {} ", this.getClass().getSimpleName(), "false");
            }
            return;
        }

        if (processorStatusReference.get().equals(ProcessorStatus.RUN)) {
            log.debug("processor [{}] is runing ", this.getClass().getSimpleName());
            return;
        }

        //String agentId = configOption.getOption(ConfigOptionDefine.AGENT_ID, null);
        String agentId = ConfigOptionDefine.getAgentId(configOption);
        log.info("collect agent : {} starting", agentId);
        List<PluginDefine> plugins = extensionLoader.getSupportedVExtensions();
        log.info("loading plugins {}...", plugins.size());
        this.start(plugins, 0);

        processorStatusReference.set(ProcessorStatus.RUN);

    }


    private void start(List<PluginDefine> loadPlugins, int index) {

        if (index >= loadPlugins.size()) {
            return;
        }
        PluginDefine pluginDefine = loadPlugins.get(index);
        ElementMatcher<? super TypeDescription> classDescription = pluginDefine.classDescription();

        String pluginName = pluginDefine.name();
        if (StringUtils.isBlank(pluginName)) {
            log.info("plugin name is empty ignored: {}", pluginDefine);
        } else {
            List<EnhanceContext> enhanceContexts = pluginDefine.enhanceContexts();
            log.info("plugin name loading: {} len {}", pluginDefine.name(), CollectionUtils.isEmpty(enhanceContexts) ? 0 : enhanceContexts.size());
            this.agentBuilder
                    .ignore(nameStartsWith("cn.bytes1024.hound"))
                    .type(ElementMatchers.not(isInterface()).and(classDescription))
                    .transform((builder, typeDescription, classLoader, module) -> enhanceRuleChainProxy.enhance(builder, enhanceContexts))
                    //TODO
                    //.with(new AgentEnhanceLister(this.enhanceDebugFactory))
                    .installOn(this.instrumentation);
        }

        this.start(loadPlugins, ++index);
    }


//    private static class AgentEnhanceLister implements AgentBuilder.Listener {
//
//        private EnhanceDebugFactory enhanceDebugFactory;
//
//        public AgentEnhanceLister(EnhanceDebugFactory enhanceDebugFactory) {
//            this.enhanceDebugFactory = enhanceDebugFactory;
//        }
//
//        @Override
//        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//
//        }
//
//        @Override
//        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
//                                     boolean loaded, DynamicType dynamicType) {
//            enhanceDebugFactory.autoWrite(typeDescription, dynamicType);
//        }
//
//        @Override
//        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
//                              boolean loaded) {
//
//        }
//
//        @Override
//        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
//                            Throwable throwable) {
//            log.error("Enhance class " + typeName + " error.", throwable);
//        }
//
//        @Override
//        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//        }
//    }


    @Override
    public void destroy() {

    }
}
