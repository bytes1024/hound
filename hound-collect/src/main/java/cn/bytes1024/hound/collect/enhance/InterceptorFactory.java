package cn.bytes1024.hound.collect.enhance;


import cn.bytes1024.hound.plugins.define.EnhanceContext;

/**
 * @author admin
 */
public interface InterceptorFactory {


    /**
     * 创建拦截器实体
     *
     * @param className :
     * @return : java.lang.Object
     * @author 江浩
     */
    Object newInterceptorObject(EnhanceContext className);


}
