package cn.bytes1024.hound.server.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transfer")
public class IndexApi {


    @PostMapping("/receive")
    public String receive(@RequestBody String content) {
        System.out.println(content);
        return "ok";
    }
}
