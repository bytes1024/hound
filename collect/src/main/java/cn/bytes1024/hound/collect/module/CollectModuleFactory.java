package cn.bytes1024.hound.collect.module;

import cn.bytes1024.hound.collect.context.ApplicationContext;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.util.Modules;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.inject.name.Names.named;

/**
 * 收集器模块工厂
 *
 * @author 江浩
 */
@Slf4j
public class CollectModuleFactory implements ModuleFactory {

    private Set<com.google.inject.Module> refModules = new HashSet<>();

    private Injector injector;

    @Override
    public ModuleFactory add(com.google.inject.Module... modules) {
        if (Objects.isNull(modules)) {
            return this;
        }
        this.refModules.addAll(Arrays.asList(modules));
        return this;
    }

    @Override
    public void init(String args, Instrumentation instrumentation) {

        defineModule(args, instrumentation);

        log.debug("init module length: {}", refModules.size());
        this.injector = Guice.createInjector(Stage.PRODUCTION, Modules.combine(refModules));

        this.injector.getInstance(ApplicationContext.class).start();
    }

    /**
     * 模块信息注入
     *
     * @return : void
     * @author 江浩
     */
    private void defineModule(String args, Instrumentation instrumentation) {

        com.google.inject.Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(Instrumentation.class).toInstance(instrumentation);
                bind(String.class).annotatedWith(named("args")).toInstance(StringUtils.isBlank(args) ? "" : args);
            }
        };

        DefineModule defineModule = new DefineModule();

        this.add(module, defineModule);
    }


}
