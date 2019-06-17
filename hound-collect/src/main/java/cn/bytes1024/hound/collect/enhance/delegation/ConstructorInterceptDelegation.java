package cn.bytes1024.hound.collect.enhance.delegation;

import cn.bytes1024.hound.plugins.define.EnhancedDefine;
import cn.bytes1024.hound.plugins.define.interceptor.ConstructorInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * @author 江浩
 */
@Slf4j
public class ConstructorInterceptDelegation {

    private ConstructorInterceptor constructorInterceptor;

    public ConstructorInterceptDelegation(ConstructorInterceptor constructorInterceptor) {
        this.constructorInterceptor = constructorInterceptor;
    }

    @RuntimeType
    public void intercept(@This Object obj,
                          @AllArguments Object[] allArguments) {
        try {
            EnhancedDefine targetObject = (EnhancedDefine) obj;
            constructorInterceptor.onConstruct(targetObject, allArguments);
        } catch (Throwable t) {
            log.error("ConstructorInter failure.", t);
        }

    }
}
