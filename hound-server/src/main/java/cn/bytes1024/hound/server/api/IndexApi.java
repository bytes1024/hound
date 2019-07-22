package cn.bytes1024.hound.server.api;

import cn.bytes1024.hound.transfers.define.TransmitTraceContent;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transfer")
public class IndexApi {


    @PostMapping("/receive")
    public String receive(@RequestBody TransmitTraceContent transmitTraceContent) {

        System.out.println(JSONObject.toJSONString(transmitTraceContent));
        return "ok";
    }
}
