package cn.bytes1024.hound.transfers.web;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.option.ConfigOptionDefine;
import cn.bytes1024.hound.transfers.define.AbstractTransmitContent;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

/**
 * rest api 数据传输器
 * <p>
 * 1.目前内嵌传输使用 okhttp，但是他又是内嵌的插件之一，循环调用的问题
 * </p>
 *
 * @author 江浩
 */
@Slf4j
public class OkHttpRestApiTransfer extends AbstractRestApiTransfer {

    private OkHttpClient okHttpClient = new OkHttpClient();

    private MediaType mediaType = MediaType.parse("application/json");


    @Override
    public <T extends AbstractTransmitContent> void transmit(ConfigOption configOption, T transmitContent) {
        RequestBody requestBody = RequestBody.create(mediaType, transmitContent.encode());
        String responseJson = this.postResponseJson(getRemoteAddress(configOption), requestBody);
        System.out.println("数据提交：" + responseJson);
    }

    public String getRemoteAddress(ConfigOption configOption) {
        return Objects.isNull(configOption) ? null : ConfigOptionDefine.getTransferWebAddress(configOption);
    }

    public String postResponseJson(String url, RequestBody requestBody) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("OKHTTP-IGNORE-DEFINE", "true")
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            ResponseBody response = call.execute().body();
            return Objects.isNull(response) ? null : response.string();
        } catch (IOException e) {
            log.info("agent WebfluxTransfer error: {}", e);
            return null;
        }
    }


}
