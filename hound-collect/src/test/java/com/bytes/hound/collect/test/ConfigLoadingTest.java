package com.bytes.hound.collect.test;

import com.bytes.hound.collect.agent.AgentOption;
import com.bytes.hound.collect.agent.DefaultAgentOption;
import com.bytes.hound.collect.context.ApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class ConfigLoadingTest {

    @Test
    public void loadTest(){

        AgentOption agentOption =  new DefaultAgentOption("E:\\test.properties");

        System.out.println(agentOption.getAgentId());

    }
}
