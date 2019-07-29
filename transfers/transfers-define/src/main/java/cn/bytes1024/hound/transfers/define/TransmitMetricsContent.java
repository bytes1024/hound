package cn.bytes1024.hound.transfers.define;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 传输指标数据
 *
 * @author 江浩
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TransmitMetricsContent implements Serializable {

    private Float cpu;

    private Float mem;

}
