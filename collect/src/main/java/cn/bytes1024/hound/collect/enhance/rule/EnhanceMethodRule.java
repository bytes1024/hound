package cn.bytes1024.hound.collect.enhance.rule;

import cn.bytes1024.hound.collect.enhance.delegation.MethodsInterceptWithDelegation;
import cn.bytes1024.hound.plugins.define.EnhanceContext;
import cn.bytes1024.hound.plugins.define.interceptor.MethodAroundInterceptor;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.isStatic;

/**
 * 方法增强的处理方式
 *
 * @author 江浩
 */
public class EnhanceMethodRule extends AbstractEnhanceRule<MethodAroundInterceptor> {


    @Override
    protected DynamicType.Builder<?> enhanceDefine(DynamicType.Builder<?> builder, MethodAroundInterceptor
            interceptPoint, EnhanceContext enhanceContext) {
        MethodDelegation methodDelegation = methodsWithDelegation(interceptPoint);
        return builder.method(ElementMatchers.not(isStatic()).and(enhanceContext.getMethodDescription())).intercept(methodDelegation);

    }

    /**
     * 方法委托处理
     *
     * @param methodsAroundInterceptor :
     * @return : net.bytebuddy.implementation.MethodDelegation
     * @author 江浩
     */
    private MethodDelegation methodsWithDelegation(MethodAroundInterceptor methodsAroundInterceptor) {
        return MethodDelegation.withDefaultConfiguration()
//                .withBinders(
//                        Morph.Binder.install(DefaultCallable.class)
//                )
                .to(new MethodsInterceptWithDelegation(methodsAroundInterceptor));
    }

}
