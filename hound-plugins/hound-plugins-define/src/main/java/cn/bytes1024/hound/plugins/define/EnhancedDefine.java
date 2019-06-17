package cn.bytes1024.hound.plugins.define;

import com.alipay.common.tracer.core.span.SofaTracerSpan;

/**
 * 增强，相当于目标添加 该接口
 *
 * @author 江浩
 */
public interface EnhancedDefine {

    /**
     * 给拦截目标设置属性
     *
     * @param sofaTracerSpan :
     * @return : void
     * @author 江浩
     */
    void setEnhancedInstanceTraceContext(SofaTracerSpan sofaTracerSpan);

    /**
     * 获取目标对象异步trace属性
     *
     * @return
     */
    SofaTracerSpan getEnhancedInstanceTraceContext();

}
