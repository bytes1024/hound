package cn.bytes1024.hound.transfers.define.buffer;

import cn.bytes1024.hound.transfers.define.DefineTransmitContent;

import java.util.List;

public interface TransferBuffer {


    /**
     * 注册监听器
     *
     * @param notifyListener :
     * @return : void
     * @author 江浩
     */
    void register(NotifyListener notifyListener);


    /**
     * 添加传输数据
     *
     * @param transmitContent :
     * @return : void
     * @author 江浩
     */
    <T extends DefineTransmitContent> void push(T transmitContent);


    /**
     * 通知监听
     *
     * @return : cn.bytes1024.hound.transfers.define.buffer.CallableListener
     * @author 江浩
     */
    List<NotifyListener> notifyListener();

}
