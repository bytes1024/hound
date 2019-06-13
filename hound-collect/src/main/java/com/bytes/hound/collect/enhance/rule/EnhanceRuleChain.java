package com.bytes.hound.collect.enhance.rule;

import net.bytebuddy.dynamic.DynamicType;

/**
 * @author 江浩
 */
public interface EnhanceRuleChain {

    /**
     * 增强实现
     *
     * @param builder
     * @param enhanceRuleCallback :
     * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
     * @author 江浩
     */
    DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, EnhanceRule.EnhanceRuleCallback enhanceRuleCallback);
}
