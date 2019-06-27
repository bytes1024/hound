package cn.bytes1024.hound.collect.enhance.rule;

import net.bytebuddy.dynamic.DynamicType;

import java.util.List;

/**
 * 默认增强实现链路
 *
 * @author 江浩
 */
public class DefaultEnhanceRuleChain implements EnhanceRuleChain {

    private int index;
    private List<EnhanceRule> enhanceRules;

    public DefaultEnhanceRuleChain(List<EnhanceRule> enhanceRules) {
        this.enhanceRules = enhanceRules;
    }

    public DefaultEnhanceRuleChain(DefaultEnhanceRuleChain parent, int index) {
        this.enhanceRules = parent.enhanceRules;
        this.index = index;
    }


    private DefaultEnhanceRuleChain chan() {
        return new DefaultEnhanceRuleChain(this, this.index + 1);
    }


    @Override
    public DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, EnhanceRule.EnhanceRuleCallback enhanceRuleCallback) {
        if (index < this.enhanceRules.size()) {
            return this.enhanceRules.get(index).enhance(chan(), builder, enhanceRuleCallback);
        }
        //default builder
        return builder;
    }
}
