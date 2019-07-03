package cn.bytes1024.hound.collect.enhance;

import cn.bytes1024.hound.collect.enhance.rule.EnhanceRule;
import cn.bytes1024.hound.plugins.define.EnhanceContext;
import net.bytebuddy.dynamic.DynamicType;

import java.util.List;

/**
 * 增强规则链路代理
 * <p>
 * 1.该代理实现最终只会执行{@link cn.bytes1024.hound.collect.enhance.rule.EnhanceRuleChain#enhance(DynamicType.Builder, EnhanceRule.EnhanceRuleOption)}
 * </p>
 *
 * @author 江浩
 */
public interface EnhanceRuleChainProxy {

    /**
     * 增强实现
     *
     * @param builder         :
     * @param enhanceContexts :
     * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
     * @author 江浩
     */
    DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, List<EnhanceContext> enhanceContexts);

}
