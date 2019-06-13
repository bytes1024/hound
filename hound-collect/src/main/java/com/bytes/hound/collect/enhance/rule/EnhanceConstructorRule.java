package com.bytes.hound.collect.enhance.rule;

import com.bytes.hound.collect.enhance.delegation.ConstructorInterceptDelegation;
import com.bytes.hound.plugins.define.EnhanceContext;
import com.bytes.hound.plugins.define.interceptor.ConstructorInterceptor;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;

/**
 * 构造器增强
 *
 * @author 江浩
 */
public class EnhanceConstructorRule extends AbstractEnhanceRule<ConstructorInterceptor> {

    @Override
    protected DynamicType.Builder<?> enhanceDefine(DynamicType.Builder<?> builder, ConstructorInterceptor interceptPoint, EnhanceContext enhanceContext) {

        MethodDelegation methodDelegation = constructorDelegation(interceptPoint);
        return builder.constructor(enhanceContext.getMethodDescription()).intercept(SuperMethodCall.INSTANCE
                .andThen(methodDelegation)
        );
    }

    private MethodDelegation constructorDelegation(ConstructorInterceptor constructorInterceptor) {
        return
                MethodDelegation.withDefaultConfiguration()
                        .to(new ConstructorInterceptDelegation(constructorInterceptor));
    }


}
