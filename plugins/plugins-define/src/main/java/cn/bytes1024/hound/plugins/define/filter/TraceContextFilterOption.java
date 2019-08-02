package cn.bytes1024.hound.plugins.define.filter;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;

/**
 * TranContextFilterOption的过滤设置
 *
 * @author 江浩
 */
public interface TraceContextFilterOption {


    /**
     * 拦截的目标class
     *
     * @author 江浩
     */
    String CLASS_NAME = "class";

    /**
     * 拦截的方法
     *
     * @author 江浩
     */
    String METHOD_NAME = "method";

    /**
     * 使用的参数
     *
     * @author 江浩
     */
    String PARAMS = "params";

    /**
     * 返回值
     *
     * @author 江浩
     */
    String RESULT = "result";


    /**
     * 异常对应key
     */
    String EXCEPTION = "exception";

    /**
     * 拦截信息过滤设置
     *
     * @param sofaTracerSpanContext :
     * @param interceptContext      :
     * @return : void
     * @author 江浩
     */
    void filterOption(SofaTracerSpanContext sofaTracerSpanContext, InterceptContext interceptContext);
}
