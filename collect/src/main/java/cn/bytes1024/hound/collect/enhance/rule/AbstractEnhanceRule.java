package cn.bytes1024.hound.collect.enhance.rule;

import cn.bytes1024.hound.collect.enhance.InterceptorFactory;
import cn.bytes1024.hound.commons.util.RefClassUtil;
import cn.bytes1024.hound.plugins.define.EnhanceContext;
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
    public DynamicType.Builder<?> enhance(EnhanceRuleChain chan, DynamicType.Builder<?> builder, EnhanceRuleOption enhanceRuleOption) {
        if (Objects.isNull(enhanceRuleOption)) {
            return builder;
        }

        try {
            InterceptorFactory interceptorFactory = enhanceRuleOption.getInterceptorFactory();
            EnhanceContext enhanceContext = enhanceRuleOption.getEnhanceContext();

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

        return chan.enhance(builder, enhanceRuleOption);
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
