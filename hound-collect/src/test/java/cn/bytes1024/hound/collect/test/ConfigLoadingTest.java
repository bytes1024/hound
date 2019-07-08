package cn.bytes1024.hound.collect.test;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.option.DefaultConfigOption;
import org.junit.Test;

public class ConfigLoadingTest {

    @Test
    public void loadTest() {

        ConfigOption configOption = new DefaultConfigOption("E:\\test.properties");

        System.out.println(configOption.getAgentId());

    }
}
