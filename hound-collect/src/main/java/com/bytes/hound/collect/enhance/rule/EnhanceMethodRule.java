package com.bytes.hound.collect.enhance.rule;

import com.bytes.hound.collect.enhance.delegation.MethodsInterceptWithOverrideArgsDelegation;
import com.bytes.hound.collect.enhance.delegation.OverrideCallable;
import com.bytes.hound.plugins.define.EnhanceContext;
import com.bytes.hound.plugins.define.interceptor.MethodAroundInterceptor;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.isStatic;

/**
 * 方法增强
 *
 * @author 江浩
 */
public class EnhanceMethodRule extends AbstractEnhanceRule<MethodAroundInterceptor> {


    @Override
    protected DynamicType.Builder<?> enhanceDefine(DynamicType.Builder<?> builder, MethodAroundInterceptor
            interceptPoint, EnhanceContext enhanceContext) {
        MethodDelegation methodDelegation = methodsWithOverrideArgsDelegation(interceptPoint);
        return builder.method(ElementMatchers.not(isStatic()).and(enhanceContext.getMethodDescription())).intercept(methodDelegation);

    }

    private MethodDelegation methodsWithOverrideArgsDelegation(MethodAroundInterceptor methodsAroundInterceptor) {
        return MethodDelegation.withDefaultConfiguration()
                .withBinders(
                        Morph.Binder.install(OverrideCallable.class)
                )
                .to(new MethodsInterceptWithOverrideArgsDelegation(methodsAroundInterceptor));
    }

}
