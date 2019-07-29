package cn.bytes1024.hound.collect.enhance.rule;

import net.bytebuddy.dynamic.DynamicType;

/**
 * @author 江浩
 */
public interface EnhanceRuleChain {

    /**
     * 增强实现
     *
     * @param builder
     * @param enhanceRuleOption :
     * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
     * @author 江浩
     */
    DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, EnhanceRule.EnhanceRuleOption enhanceRuleOption);
}
