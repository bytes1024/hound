package cn.bytes1024.hound.collect.enhance.delegation;

/**
 * 自定义代理实现方法
 *
 * @author 江浩
 */
@Deprecated
public interface DefaultCallable {
    /**
     * invoker
     *
     * @param args :
     * @return : java.lang.Object
     * @author 江浩
     */
    Object invoker(Object[] args);
}
