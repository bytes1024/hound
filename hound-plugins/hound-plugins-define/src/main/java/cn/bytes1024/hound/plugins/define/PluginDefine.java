package cn.bytes1024.hound.plugins.define;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.List;

/**
 * 插件定义
 * @author 江浩
 */
public interface PluginDefine {

    /**
     * 插件名称
     * @return
     */
    String name();

    /**
     * 增强拦截类
     *
     * @return : net.bytebuddy.matcher.ElementMatcher<? super net.bytebuddy.description.type.TypeDescription>
     * @author 江浩
     */
    ElementMatcher<? super TypeDescription> classDescription();


    /**
     * 增强内容
     *
     * @return : java.util.List<com.support.monitor.agent.core.context.EnhanceContext>
     * @author 江浩
     */
    List<EnhanceContext> enhanceContexts();
}
