package cn.bytes1024.hound.commons.option;

public class ConfigOptionDefine {


    private static final String PREFIX = "bytes.hound.";

    public static final String AGENT_ID = PREFIX + "agent.id";

    public static final String TRACER_TYPE = PREFIX + "tracer.type";

    public static final String TRANSFER_TYPE = PREFIX + "transfer.type";

    public static String getAgentId(ConfigOption configOption) {
        return configOption.getOption(AGENT_ID, null);
    }


    public static String getTracerType(ConfigOption configOption) {
        return configOption.getOption(TRACER_TYPE, null);
    }


    public static String getTransferType(ConfigOption configOption) {
        return configOption.getOption(TRANSFER_TYPE, null);
    }


}
