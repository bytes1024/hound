package com.bytes.hound.collect.enhance;

import com.bytes.hound.plugins.define.EnhanceContext;
import net.bytebuddy.dynamic.DynamicType;

import java.util.List;

/**
 * 增强实现工厂
 *
 * @author 江浩
 */
public interface EnhanceFactory {

   /**
    * 增强实现
    * @param builder : 
    * @param enhanceContexts :
    * @return : net.bytebuddy.dynamic.DynamicType.Builder<?>
    * @author 江浩
    */
    DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder, List<EnhanceContext> enhanceContexts);

}
