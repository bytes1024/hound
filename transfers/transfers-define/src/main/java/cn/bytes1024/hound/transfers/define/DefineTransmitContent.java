package cn.bytes1024.hound.transfers.define;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class DefineTransmitContent implements Serializable {

    public DefineTransmitContent() {
    }

    public String encode() {
        return JSONObject.toJSONString(this);
    }

    public <T> T decode(String content, Class<T> tClass) {
        return JSONObject.parseObject(content, tClass);
    }


}
