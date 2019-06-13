package com.bytes.hound.plugins.webflux;

import com.bytes.hound.plugins.define.AbstractPluginDefine;
import com.bytes.hound.plugins.webflux.interceptor.OnInboundNextMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * reactor
 *
 * @author 江浩
 */
public class WebFluxOnInboundNextPlugin extends AbstractPluginDefine {
    @Override
    public void init(AbstractPluginDefine.PluginDefineBuilder defineBuilder) {

        defineBuilder
                .pointName("spring-web-flux-onInboundNext")
                .pointClass(named("reactor.netty.http.server.HttpServerOperations"))
                .pointMethod(named("onInboundNext"), OnInboundNextMethodInterceptor.class);
    }
}
