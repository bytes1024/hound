package com.bytes.hound.collect.enhance.rule;

import com.bytes.hound.plugins.define.EnhanceContext;
import com.bytes.hound.plugins.define.interceptor.StaticMethodAroundInterceptor;
import net.bytebuddy.dynamic.DynamicType;

/**
 * 静态方法增强
 *
 * @author 江浩
 */
public class EnhanceStaticMethodRule extends AbstractEnhanceRule<StaticMethodAroundInterceptor> {

    @Override
    protected DynamicType.Builder<?> enhanceDefine(DynamicType.Builder<?> builder, StaticMethodAroundInterceptor interceptPoint, EnhanceContext enhanceContext) {

        //TODO
        return null;
    }
}
