package cn.bytes1024.hound.collect.context;


import com.alipay.common.tracer.core.span.SofaTracerSpan;

/**
 * 单个线程活跃span的操作信息
 * <p>
 * LIFO   先进后出 栈原则操作
 * </p>
 *
 * @author 江浩
 */
public interface ActiveTracerSpan {


    /**
     * 添加在栈头部
     *
     * @param sofaTracerSpan
     */
    void addFirst(SofaTracerSpan sofaTracerSpan);


    /**
     * 当前线程执行长度
     *
     * @return : java.lang.Integer
     * @author 江浩
     */
    Integer size();

    /**
     * 最后一个提交的，LIFO 原则
     *
     * @return : com.alipay.common.tracer.core.span.SofaTracerSpan
     * @author 江浩
     */
    SofaTracerSpan peekFirst();

    /**
     * 停止最后一个提交信息
     *
     * @return : com.alipay.common.tracer.core.span.SofaTracerSpan
     * @author 江浩
     */
    SofaTracerSpan pollFirst();
}
