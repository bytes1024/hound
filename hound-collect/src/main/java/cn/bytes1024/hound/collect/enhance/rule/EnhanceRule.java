package cn.bytes1024.hound.collect.enhance.rule;

import cn.bytes1024.hound.collect.enhance.InterceptorFactory;
import cn.bytes1024.hound.plugins.define.EnhanceContext;
import net.bytebuddy.dynamic.DynamicType;

/**
 * 规则信息
 *
 * @author 江浩
 */
public interface EnhanceRule {


    /**
     * 标识
     *
     * @author 江浩
     */
    class Key {
        public static final String SOURCE = "source";
        public static final String METHOD = "method";
        public static final String CONSTRUCTOR = "constructor";
        public static final String STATIC_METHOD = "static_method";
    }

    interface EnhanceRuleCallback {

        InterceptorFactory getInterceptorFactory();

        EnhanceContext getEnhanceContext();

    }


    /**
     * 增强规则实现
     *
     * @param chan                :
     * @param builder
     * @param enhanceRuleCallback :
     * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
     * @author 江浩
     */
    DynamicType.Builder<?> enhance(EnhanceRuleChain chan,
                                   DynamicType.Builder<?> builder,
                                   EnhanceRuleCallback enhanceRuleCallback);

    /**
     * 过滤掉
     *
     * @return : boolean
     * @author 江浩
     */
    default boolean need() {
        return false;
    }

}
