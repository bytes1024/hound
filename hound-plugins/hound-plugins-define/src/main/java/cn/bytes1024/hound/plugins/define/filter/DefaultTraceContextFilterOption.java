package cn.bytes1024.hound.plugins.define.filter;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * 默认的拦截过滤设置
 *
 * @author 江浩
 */
public class DefaultTraceContextFilterOption implements TraceContextFilterOption {


    @Override
    public void filterOption(SofaTracerSpanContext sofaTracerSpanContext, InterceptContext interceptContext) {
        sofaTracerSpanContext.setSysBaggageItem(CLASS_NAME, interceptContext.getTarget().getClass().getName());
        sofaTracerSpanContext.setSysBaggageItem(METHOD_NAME, interceptContext.getMethod().getName());
        this.filterParamsOption(sofaTracerSpanContext, interceptContext.getMethod().getParameters(), interceptContext.getArgs());
        this.filterResultOption(sofaTracerSpanContext, interceptContext.getResult());
    }

    private void filterParamsOption(SofaTracerSpanContext sofaTracerSpanContext, Parameter[] parameters, Object[] interceptContextArgs) {
        if (Objects.isNull(parameters)) {
            return;
        }

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            //TODO 过滤参数类型定义
        }


    }

    private void filterResultOption(SofaTracerSpanContext sofaTracerSpanContext, Object result) {
        if (Objects.isNull(result)) {
            return;
        }
        if (this.isInterface(result)) {
            return;
        }
    }

    private boolean isInterface(Object object) {
        return object.getClass().isInterface();
    }


    @Data
    @Builder
    final static class KVOption {
        private String k;
        private Object v;
    }


}
