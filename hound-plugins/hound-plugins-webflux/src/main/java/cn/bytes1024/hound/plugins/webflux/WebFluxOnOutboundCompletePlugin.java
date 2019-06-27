package cn.bytes1024.hound.plugins.webflux;


import cn.bytes1024.hound.plugins.define.AbstractPluginDefine;
import cn.bytes1024.hound.plugins.webflux.interceptor.OnOutboundCompleteMethodInterceptor;

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
