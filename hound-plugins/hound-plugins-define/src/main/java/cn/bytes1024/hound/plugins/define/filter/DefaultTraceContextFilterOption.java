package cn.bytes1024.hound.plugins.define.filter;

import cn.bytes1024.hound.plugins.define.InterceptContext;
import com.alibaba.fastjson.JSONObject;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
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
        //TODO 参数跟返回值的传递
        //this.filterParamsOption(sofaTracerSpanContext, interceptContext.getMethod().getParameters(), interceptContext.getArgs());
        //this.filterResultOption(sofaTracerSpanContext, interceptContext.getResult());
    }

    /**
     * 过滤参数设置
     * <p>
     * 1.目前插件中可能存在接口的定义
     * </p>
     *
     * @param sofaTracerSpanContext :
     * @param parameters            :
     * @param args                  :
     * @return : void
     * @author 江浩
     */
    private void filterParamsOption(SofaTracerSpanContext sofaTracerSpanContext, Parameter[] parameters, Object[] args) {
        if (Objects.isNull(parameters)) {
            return;
        }

        List<KVOption> kvOptions = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> aClass = parameter.getType();
            KVOption.KVOptionBuilder kvOptionBuilder = KVOption.builder();
            kvOptionBuilder.optionKey(aClass.getName());
            if (!isIgnore(aClass)) {
                kvOptionBuilder.optionValue(args[i]);
            }
            kvOptions.add(kvOptionBuilder.build());
        }
        //json?
        sofaTracerSpanContext.setSysBaggageItem(PARAMS, format(kvOptions));
    }

    private boolean isIgnore(Class<?> aClass) {
        return aClass.isInterface();
    }


    private void filterResultOption(SofaTracerSpanContext sofaTracerSpanContext, Object result) {
        if (Objects.isNull(result)) {
            return;
        }
        Class<?> rClass = result.getClass();
        if (this.isIgnore(rClass)) {
            return;
        }

        String resultFormat = this.format(KVOption.builder()
                .optionKey(rClass.getName())
                .optionValue(result).build());
        sofaTracerSpanContext.setSysBaggageItem(RESULT, resultFormat);
    }


    private String format(KVOption kvOption) {
        try {
            return JSONObject.toJSONString(kvOption);
        } catch (Exception e) {
            return null;
        }
    }

    private String format(List<KVOption> kvOptions) {
        try {
            return JSONObject.toJSONString(kvOptions);
        } catch (Exception e) {
            return null;
        }
    }


    @Data
    @Builder
    final static class KVOption {
        private String optionKey;
        private Object optionValue;
    }


}
