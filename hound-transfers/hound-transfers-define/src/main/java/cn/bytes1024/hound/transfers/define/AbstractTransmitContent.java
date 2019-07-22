package cn.bytes1024.hound.transfers.define;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public abstract class AbstractTransmitContent implements Serializable {


    public String encode() {
        return JSONObject.toJSONString(this);
    }

    public <T> T decode(String content, Class<T> tClass) {
        return JSONObject.parseObject(content, tClass);
    }


}
