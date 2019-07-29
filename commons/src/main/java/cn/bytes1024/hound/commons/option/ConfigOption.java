package cn.bytes1024.hound.commons.option;

import java.util.List;

/**
 * 配置加载信息
 *
 * @author 江浩
 */
public interface ConfigOption {


    /**
     * 加载配置信息
     *
     * @param path :
     * @return : void
     * @author 江浩
     */
    void load(String path);


//    /**
//     * 获取探针编号
//     *
//     * @return : java.lang.String
//     * @author 江浩
//     */
//    String getAgentId();
//
//    /**
//     * 获取传输方式
//     *
//     * @return
//     */
//    String getTransfer();
//
//    /**
//     * tracer类型
//     *
//     * @return : java.lang.String
//     * @author 江浩
//     */
//    String getTracerType();

    /**
     * 获取属性信息
     *
     * @param key          :
     * @param defaultValue :
     * @return : java.lang.String
     * @author 江浩
     */
    String getOption(String key, String defaultValue);


    /**
     * 默认获取一批获取
     *
     * @param key :
     * @return : java.util.List<java.lang.String>
     * @author 江浩
     */
    List<String> getOptions(String key);


}
