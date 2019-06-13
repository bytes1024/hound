package com.bytes.hound.collect.enhance;

import com.bytes.hound.collect.enhance.rule.DefaultEnhanceRuleChain;
import com.bytes.hound.collect.enhance.rule.EnhanceRule;
import com.bytes.hound.plugins.define.EnhanceContext;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.DynamicType;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 默认method delegation构建工厂
 *
 * @author
 */
@Slf4j
public class DefaultEnhanceFactory implements EnhanceFactory {


    private List<EnhanceRule> enhanceRules;

    private InterceptorFactory interceptorFactory;


    public DefaultEnhanceFactory(List<EnhanceRule> enhanceRules, InterceptorFactory interceptorFactory) {
        this.enhanceRules = enhanceRules;
        this.interceptorFactory = interceptorFactory;
    }


    private DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, EnhanceContext enhanceContext) {
        return new DefaultEnhanceRuleChain(this.enhanceRules).enhance(builder, new DefaultEnhanceRuleCallback(enhanceContext, this.interceptorFactory));
    }

    /**
     * 增强处理
     *
     * @param builder         :
     * @param enhanceContexts :
     * @param index           :
     * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
     * @author 江浩
     */
    private DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, List<EnhanceContext> enhanceContexts, int index) {
        //#to long bug ?
        if (CollectionUtils.isEmpty(enhanceContexts) || index >= enhanceContexts.size()) {
            return builder;
        }
        EnhanceContext enhanceContext = enhanceContexts.get(index);
        builder = this.enhance(builder, enhanceContext);
        return enhance(builder, enhanceContexts, ++index);
    }

    @Override
    public DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, List<EnhanceContext> enhanceContexts) {

        return this.enhance(builder, enhanceContexts, 0);
    }


    /**
     * callback
     */
    public static class DefaultEnhanceRuleCallback implements EnhanceRule.EnhanceRuleCallback {

        private InterceptorFactory interceptorFactory;

        private EnhanceContext enhanceContext;


        public DefaultEnhanceRuleCallback(EnhanceContext enhanceContext,
                                          InterceptorFactory interceptorFactory) {
            this.interceptorFactory = interceptorFactory;
            this.enhanceContext = enhanceContext;
        }

        @Override
        public InterceptorFactory getInterceptorFactory() {
            return this.interceptorFactory;
        }

        @Override
        public EnhanceContext getEnhanceContext() {
            return this.enhanceContext;
        }


    }

}
