package cn.bytes1024.hound.transfers.define.buffer;


import cn.bytes1024.hound.transfers.define.DefineTransmitContent;

public interface NotifyListener {
    /**
     * 通知消息监听
     *
     * @param message :
     * @return : void
     * @author 江浩
     */
    <T extends DefineTransmitContent> void notify(T message);

}
