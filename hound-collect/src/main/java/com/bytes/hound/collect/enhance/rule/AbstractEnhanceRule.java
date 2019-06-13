package com.bytes.hound.collect.enhance.rule;

import com.bytes.hound.collect.enhance.InterceptorFactory;
import com.bytes.hound.commons.util.RefClassUtil;
import com.bytes.hound.plugins.define.EnhanceContext;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.DynamicType;

import java.util.Objects;

/**
 * 增强链路默认增强对象应该参与所有的增强方式
 * <p>
 * 具体是否增强应该由子类自行判断
 * </p>
 *
 * @author 江浩
 */
@Slf4j
public abstract class AbstractEnhanceRule<R> implements EnhanceRule {

    private Class rClass;


    public AbstractEnhanceRule() {
        rClass = RefClassUtil.getSuperClassGenricType(this.getClass(), 0);
    }

    @Override
    public DynamicType.Builder<?> enhance(EnhanceRuleChain chan, DynamicType.Builder<?> builder, EnhanceRuleCallback enhanceRuleCallback) {
        if (Objects.isNull(enhanceRuleCallback)) {
            return builder;
        }

        try {
            InterceptorFactory interceptorFactory = enhanceRuleCallback.getInterceptorFactory();
            EnhanceContext enhanceContext = enhanceRuleCallback.getEnhanceContext();

            Object object = interceptorFactory.newInterceptorObject(enhanceContext);

            if (rClass.isAssignableFrom(object.getClass()) || need()) {
                DynamicType.Builder<?> newBuilder = this.enhanceDefine(builder, (R) object, enhanceContext);
                if (!Objects.isNull(newBuilder)) {
                    builder = newBuilder;
                }
            }
        } catch (Exception e) {
            log.error("enhance  error : {}", e);
        }

        return chan.enhance(builder, enhanceRuleCallback);
    }

    /**
     * 默认增强方式
     *
     * @param builder        :
     * @param interceptPoint
     * @param enhanceContext :
     * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
     * @author 江浩
     */
    protected abstract DynamicType.Builder<?> enhanceDefine(DynamicType.Builder<?> builder, R interceptPoint, EnhanceContext enhanceContext);
}
