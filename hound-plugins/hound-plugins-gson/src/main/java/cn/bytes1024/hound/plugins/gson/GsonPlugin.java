package cn.bytes1024.hound.plugins.gson;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.gson.intercepotr.GsonMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * gson插件
 *
 * @author 江浩
 */
public class GsonPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {


        //Reader json, Type typeOfT
        //JsonElement jsonElement, Appendable writer
//        defineBuilder.pointName("plugin-gson")
//                .pointClass(named("com.google.gson.Gson"))
//                .pointMethod(
//                        named("toJson")
//                                .and(takesArgument(0, JsonElement.class)
//                                        .and(takesArgument(1, JsonWriter.class)))
//                                .or(named("fromJson").and(takesArgument(0, Reader.class)
//                                        .and(takesArgument(1, Type.class))))
//                        , GsonMethodInterceptor.class);


        defineBuilder.pointName("plugin-gson")
                .pointClass(named("com.google.gson.Gson"))
                .pointMethod(
                        named("toJson")
                                .or(named("fromJson"))
                        , GsonMethodInterceptor.class);
    }
}
