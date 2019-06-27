package cn.bytes1024.hound.collect.test;

import cn.bytes1024.hound.collect.agent.AgentOption;
import cn.bytes1024.hound.collect.agent.DefaultAgentOption;
import org.junit.Test;

public class ConfigLoadingTest {

    @Test
    public void loadTest() {

        AgentOption agentOption = new DefaultAgentOption("E:\\test.properties");

        System.out.println(agentOption.getAgentId());

    }
}
