package cn.bytes1024.hound.plugins.httpclient;

import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.httpclient.interceptor.HttpClientMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * httpClient
 *
 * @author 江浩
 */
public class HttpClientPlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {
        defineBuilder.pointName("plugin-httpClient")
                .pointClass(named("org.apache.http.impl.client.MinimalHttpClient")
                        .or(named("org.apache.http.impl.client.InternalHttpClient"))
                        .or(named("org.apache.http.impl.client.AbstractHttpClient")))
                .pointMethod(named("execute"), HttpClientMethodInterceptor.class);
    }
}
