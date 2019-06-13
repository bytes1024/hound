package com.bytes.hound.collect.module;

import com.google.inject.Module;

import java.lang.instrument.Instrumentation;

/**
 * 模块管理
 * @author 江浩
 */
public interface ModuleFactory {


    /**
     * 添加管理模块信息
     * @param modules :
     * @return : void
     * @author 江浩
     */
    ModuleFactory add(Module ... modules);

    /**
     * 模块的初始加载
     * @param args :
     * @param instrumentation :
     * @return : void
     * @author 江浩
     */
    void init(String args, Instrumentation instrumentation);

}
