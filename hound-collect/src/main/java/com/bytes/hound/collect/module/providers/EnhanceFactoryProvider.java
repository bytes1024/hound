package com.bytes.hound.collect.module.providers;

import com.bytes.hound.collect.enhance.DefaultEnhanceFactory;
import com.bytes.hound.collect.enhance.EnhanceFactory;
import com.bytes.hound.collect.enhance.InterceptorFactory;
import com.bytes.hound.collect.enhance.rule.EnhanceRule;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import java.util.List;

/**
 * 增强处理工厂
 *
 * @author 江浩
 */
public class EnhanceFactoryProvider implements Provider<EnhanceFactory> {


    private List<EnhanceRule> enhanceRules = Lists.newArrayList();

    private InterceptorFactory interceptorFactory;

    @Inject
    public EnhanceFactoryProvider(
            InterceptorFactory interceptorFactory,
            @Named(EnhanceRule.Key.METHOD) EnhanceRule enhanceMethod,
            @Named(EnhanceRule.Key.CONSTRUCTOR) EnhanceRule enhanceConstructor,
            @Named(EnhanceRule.Key.STATIC_METHOD) EnhanceRule enhanceStatic,
            @Named(EnhanceRule.Key.SOURCE) EnhanceRule enhanceSource) {
        this.interceptorFactory = interceptorFactory;
        this.enhanceRules.add(enhanceConstructor);
        this.enhanceRules.add(enhanceMethod);
        this.enhanceRules.add(enhanceStatic);
        this.enhanceRules.add(enhanceSource);
    }

    @Override
    public EnhanceFactory get() {
        return new DefaultEnhanceFactory(this.enhanceRules, this.interceptorFactory);
    }
}
