package cn.bytes1024.hound.plugins.fastjson;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.fastjson.interceptor.FastJsonMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * fastJson插件信息
 *
 * @author 江浩
 */
public class FastJsonPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {

        defineBuilder.pointName("plugin-fastJson")
                .pointClass(named("com.alibaba.fastjson.JSON"))
                .pointMethod(
                        named("parse")
                                .or(named("parseObject"))
                                .or(named("parseArray"))
                                .or(named("toJSON"))
                                .or(named("toJavaObject"))
                                .or(named("toJSONString"))
                                .or(named("toJSONBytes"))
                                .or(named("writeJSONString"))
                        ,
                        FastJsonMethodInterceptor.class

                );
    }
}
