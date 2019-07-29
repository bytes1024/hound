package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.collect.enhance.DefaultEnhanceRuleChainProxy;
import cn.bytes1024.hound.collect.enhance.EnhanceRuleChainProxy;
import cn.bytes1024.hound.collect.enhance.InterceptorFactory;
import cn.bytes1024.hound.collect.enhance.rule.EnhanceRule;
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
public class EnhanceRuleChainProxyProvider implements Provider<EnhanceRuleChainProxy> {


    private List<EnhanceRule> enhanceRules = Lists.newArrayList();

    private InterceptorFactory interceptorFactory;

    @Inject
    public EnhanceRuleChainProxyProvider(
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
    public EnhanceRuleChainProxy get() {
        return new DefaultEnhanceRuleChainProxy(this.enhanceRules, this.interceptorFactory);
    }
}
