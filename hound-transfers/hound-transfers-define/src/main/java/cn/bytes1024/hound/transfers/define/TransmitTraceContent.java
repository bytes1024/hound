package cn.bytes1024.hound.transfers.define;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 传输对象
 * <p>特定传输对象避免对个数据来源点数据不一致</p>
 * <p>
 * 1.agent
 * 2.sdk
 * </p>
 *
 * @author 江浩
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TransmitTraceContent extends DefineTransmitContent {

    private String operationName;

    private String traceId;

    private String spanId;

    private String parentId;

    private Boolean sampled;

    private Map<String, String> bizBaggage;

    private Map<String, String> sysBaggage;

    private Long startTime;

    private Long endTime;
}
