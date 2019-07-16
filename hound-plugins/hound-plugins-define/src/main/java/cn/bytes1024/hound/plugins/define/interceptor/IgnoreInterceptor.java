package cn.bytes1024.hound.plugins.define.interceptor;

/**
 * 处理支持过滤方式
 *
 * @author 江浩
 */
public interface IgnoreInterceptor<R> {

    boolean ignore(R handler);
}
