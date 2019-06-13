package com.bytes.hound.collect.module;

import com.bytes.hound.collect.context.ApplicationContext;
import com.google.inject.*;
import com.google.inject.util.Modules;
import org.apache.commons.lang3.StringUtils;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.inject.name.Names.named;

/**
 * 收集器模块工厂
 * @author 江浩
 */
public class CollectModuleFactory implements ModuleFactory {

    private Set<Module> refModules = new HashSet<>();

    private Injector injector;

    @Override
    public ModuleFactory add(Module... modules) {
        if(Objects.isNull(modules)) {
            return this;
        }
        this.refModules.addAll(Arrays.asList(modules));
        return this;
    }

    @Override
    public void init(String args, Instrumentation instrumentation) {

        defineModule( args,instrumentation);

        this.injector= Guice.createInjector(Stage.PRODUCTION, Modules.combine(refModules));

        this.injector.getInstance(ApplicationContext.class).start();
    }

    /**
     * 模块信息注入
     * @return : void
     * @author 江浩
     */
    private void defineModule(String args,Instrumentation instrumentation) {

        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(Instrumentation.class).toInstance(instrumentation);
                bind(String.class).annotatedWith(named("args")).toInstance(StringUtils.isBlank(args)? "" : args);
            }
        };

        DefineModule defineModule = new DefineModule();

        this.add(module,defineModule);
    }



}
