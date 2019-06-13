package com.bytes.hound.collect.handler;

import com.bytes.hound.collect.agent.AgentOption;
import com.bytes.hound.collect.enhance.EnhanceFactory;
import com.bytes.hound.loader.ExtensionLoader;
import com.bytes.hound.plugins.define.EnhanceContext;
import com.bytes.hound.plugins.define.PluginDefine;
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
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;

/**
 * @author 江浩
 */
@Slf4j
public class CollectAgentHandler implements Handler {

    private AgentBuilder agentBuilder;

    private Instrumentation instrumentation;

    private EnhanceFactory enhanceFactory;

    private ExtensionLoader<PluginDefine> extensionLoader = ExtensionLoader.getExtensionLoader(PluginDefine.class);

    @Inject
    public CollectAgentHandler(AgentBuilder agentBuilder,
                               Instrumentation instrumentation,
                               EnhanceFactory enhanceFactory){
        this.agentBuilder = agentBuilder;
        this.instrumentation = instrumentation;
        this.enhanceFactory = enhanceFactory;
    }

    @Override
    public void handle(AgentOption agentOption, CountDownLatch countDownLatch) {
        if(Objects.isNull(countDownLatch)){
            return;
        }

        String agentId = agentOption.getAgentId();
        log.info("collect agent : {} starting",agentId);
        List<PluginDefine> plugins = extensionLoader.getSupportedVExtensions();
        log.info("loading plugins {}...",plugins.size());
        this.handle(plugins,0);

        countDownLatch.countDown();
    }


    private void handle(List<PluginDefine> loadPlugins,int index){

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
            this.agentBuilder.type(ElementMatchers.not(isInterface()).and(classDescription))
                    .transform((builder, typeDescription, classLoader, module) -> enhanceFactory.enhance(builder, enhanceContexts))
                    //TODO
                    //.with(new AgentEnhanceLister(this.enhanceDebugFactory))
                    .installOn(this.instrumentation);
        }

        this.handle(loadPlugins, ++index);
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
