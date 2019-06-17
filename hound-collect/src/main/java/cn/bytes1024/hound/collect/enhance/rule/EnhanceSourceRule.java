package cn.bytes1024.hound.collect.enhance.rule;

import cn.bytes1024.hound.plugins.define.EnhanceContext;
import cn.bytes1024.hound.plugins.define.EnhancedDefine;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;

import java.util.Objects;

import static net.bytebuddy.jar.asm.Opcodes.ACC_PRIVATE;
import static net.bytebuddy.jar.asm.Opcodes.ACC_VOLATILE;

/**
 * @author 江浩
 */
public class EnhanceSourceRule extends AbstractEnhanceRule<Object> {

    private static final String ENHANCE_CLASS_FIELD_NAME = "_$ENHANCE_CLASS_FIELD_NAME";

    @Override
    protected DynamicType.Builder<?> enhanceDefine(DynamicType.Builder<?> builder, Object interceptPoint, EnhanceContext enhanceContext) {
        //TODO 有优化的可能不
        TypeList.Generic generics = builder.make().getTypeDescription().getInterfaces();
        if (!Objects.isNull(generics) && !generics.isEmpty()) {
            for (int i = 0; i < generics.size(); i++) {
                if (generics.get(i).getTypeName().equalsIgnoreCase(EnhancedDefine.class.getName())) {
                    return builder;
                }
            }
        }

        //EnhanceDefine handler param of TraceIdRecorder
        return builder.defineField(ENHANCE_CLASS_FIELD_NAME, SofaTracerSpan.class, ACC_PRIVATE | ACC_VOLATILE)
                .implement(EnhancedDefine.class)
                .intercept(FieldAccessor.ofField(ENHANCE_CLASS_FIELD_NAME));

    }

    @Override
    public boolean need() {
        return true;
    }
}
