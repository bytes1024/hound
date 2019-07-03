package cn.bytes1024.hound.collect.enhance;

import cn.bytes1024.hound.collect.enhance.rule.DefaultEnhanceRuleChain;
import cn.bytes1024.hound.collect.enhance.rule.EnhanceRule;
import cn.bytes1024.hound.collect.enhance.rule.EnhanceRuleChain;
import cn.bytes1024.hound.plugins.define.EnhanceContext;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.DynamicType;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 增强链路代理
 *
 * @author
 */
@Slf4j
public class DefaultEnhanceRuleChainProxy implements EnhanceRuleChainProxy, EnhanceRuleChain {


    private List<EnhanceRule> enhanceRules;

    private InterceptorFactory interceptorFactory;

    public DefaultEnhanceRuleChainProxy(List<EnhanceRule> enhanceRules, InterceptorFactory interceptorFactory) {
        this.enhanceRules = enhanceRules;
        this.interceptorFactory = interceptorFactory;
    }


    private DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, EnhanceContext enhanceContext) {
        return this.enhance(builder, new DefaultEnhanceRuleOption(enhanceContext, this.interceptorFactory));
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

    @Override
    public DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, EnhanceRule.EnhanceRuleOption enhanceRuleOption) {
        return new DefaultEnhanceRuleChain(this.enhanceRules).enhance(builder, enhanceRuleOption);
    }


    /**
     * callback
     */
    public static class DefaultEnhanceRuleOption implements EnhanceRule.EnhanceRuleOption {

        private InterceptorFactory interceptorFactory;

        private EnhanceContext enhanceContext;

        public DefaultEnhanceRuleOption(EnhanceContext enhanceContext,
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
