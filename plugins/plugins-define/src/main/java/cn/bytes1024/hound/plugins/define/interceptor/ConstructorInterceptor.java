package cn.bytes1024.hound.plugins.define.interceptor;


import cn.bytes1024.hound.plugins.define.EnhancedDefine;

/**
 * 构造器拦截器
 *
 * @author 江浩
 */
public interface ConstructorInterceptor {

    /**
     * 构造器执行
     *
     * @param enhancedDefine :
     * @param allArguments   :
     * @return : void
     * @author 江浩
     */
    void onConstruct(EnhancedDefine enhancedDefine, Object[] allArguments);
}
