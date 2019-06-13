package com.bytes.hound.collect.module.providers;

import com.bytes.hound.collect.agent.AgentOption;
import com.bytes.hound.collect.agent.DefaultAgentOption;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * @author 江浩
 */
public class AgentOptionProvider implements Provider<AgentOption> {

    private String args;

    @Inject
    public AgentOptionProvider(@Named("args") String args) {
        this.args = args;
    }

    @Override
    public AgentOption get() {
       return new DefaultAgentOption(this.args);
    }
}
