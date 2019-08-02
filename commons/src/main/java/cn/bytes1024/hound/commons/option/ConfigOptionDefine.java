package cn.bytes1024.hound.commons.option;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigOptionDefine {

    private static final String PREFIX = "bytes.hound.";


    private static final String AGENT_ENABLED = PREFIX + "agent.enabled";

    private static final String AGENT_ID = PREFIX + "agent.id";

    private static final String TRACER_TYPE = PREFIX + "tracer.type";

    private static final String TRANSFER_ENABLED = PREFIX + "transfer.enabled";

    private static final String TRANSFER_CONTENT_VIEW_ENABLED = PREFIX + "transfer.content.show.enabled";

    private static final String TRANSFER_TYPE = PREFIX + "transfer.type";

    private static final String TRANSFER_WEB_ADDRESS = PREFIX + "transfer.web.address";

    private static final String BUFFER_MAX = PREFIX + "transfer.batch.max";


    public static String getAgentId(ConfigOption configOption) {
        return configOption.getOption(AGENT_ID, null);
    }


    /**
     * tracerType 必须有一个默认值
     *
     * @param configOption :
     * @return : java.lang.String
     * @author 江浩
     */
    public static String getTracerType(ConfigOption configOption) {
        return configOption.getOption(TRACER_TYPE, "default");
    }


    public static String getTransferType(ConfigOption configOption) {
        return configOption.getOption(TRANSFER_TYPE, null);
    }

    /**
     * 是否开启了远程传输
     *
     * @param configOption :
     * @return : boolean
     * @author 江浩
     */
    public static boolean isTransferEnabled(ConfigOption configOption) {
        String bool = configOption.getOption(TRANSFER_ENABLED, "false");
        try {
            return Boolean.parseBoolean(bool);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("transfer.enabled error:{}", bool);
            }
            return false;
        }
    }


    public static boolean isOpenTransmitContentView(ConfigOption configOption) {
        String bool = configOption.getOption(TRANSFER_CONTENT_VIEW_ENABLED, "false");
        try {
            return Boolean.parseBoolean(bool);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("transfer.enabled error:{}", bool);
            }
            return false;
        }
    }

    public static String getTransferWebAddress(ConfigOption configOption) {
        return configOption.getOption(TRANSFER_WEB_ADDRESS, null);
    }


    public static boolean isEnabledAgent(ConfigOption configOption) {
        String bool = configOption.getOption(AGENT_ENABLED, "true");
        try {
            return Boolean.parseBoolean(bool);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("transfer.enabled error:{}", bool);
            }
            return false;
        }
    }


    public static int getBufferMaxNumber(ConfigOption configOption) {
        String number = configOption.getOption(BUFFER_MAX, String.valueOf(Short.MAX_VALUE));
        return Integer.parseInt(number);
    }
}
