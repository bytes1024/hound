package com.bytes.hound.plugins.webflux;

import com.bytes.hound.plugins.define.AbstractPluginDefine;
import com.bytes.hound.plugins.webflux.interceptor.OnOutboundCompleteMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * reactor
 *
 * @author 江浩
 */
public class WebFluxOnOutboundCompletePlugin extends AbstractPluginDefine {
    @Override
    public void init(PluginDefineBuilder defineBuilder) {

        defineBuilder
                .pointName("spring-web-flux-onOutboundComplete")
                .pointClass(named("reactor.netty.http.server.HttpServerOperations"))
                .pointMethod(named("onOutboundComplete"), OnOutboundCompleteMethodInterceptor.class);
    }
}
