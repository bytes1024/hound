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

    /**
     * 增强规则执行设置信息
     *
     * @author 江浩
     */
    interface EnhanceRuleOption {

        /**
         * 拦截器工厂
         *
         * @return : cn.bytes1024.hound.collect.enhance.InterceptorFactory
         * @author 江浩
         */
        InterceptorFactory getInterceptorFactory();

        /**
         * 获取增强上线文环境
         *
         * @return : cn.bytes1024.hound.plugins.define.EnhanceContext
         * @author 江浩
         */
        EnhanceContext getEnhanceContext();

    }


    /**
     * 增强规则实现
     *
     * @param chan              :
     * @param builder
     * @param enhanceRuleOption :
     * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
     * @author 江浩
     */
    DynamicType.Builder<?> enhance(EnhanceRuleChain chan,
                                   DynamicType.Builder<?> builder,
                                   EnhanceRuleOption enhanceRuleOption);

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
