package cn.bytes1024.hound.plugins.okhttp;


import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.okhttp.interceptor.OkHttpClientMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * @author 江浩
 */
public class OkHttpClientPlugin extends AbstractPluginDefine {
    @Override
    public void init(AbstractPluginDefine.PluginDefineBuilder defineBuilder) {

        defineBuilder
                .pointName("plugin-okHttp")
                .pointClass(named("okhttp3.OkHttpClient"))
                .pointMethod(named("newCall")
                                .and(takesArguments(1))
                                .and(takesArgument(0, named("okhttp3.Request")))
                        , OkHttpClientMethodInterceptor.class);

    }
}
