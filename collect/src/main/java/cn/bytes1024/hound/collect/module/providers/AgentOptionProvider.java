package cn.bytes1024.hound.collect.module.providers;

import cn.bytes1024.hound.commons.option.ConfigOption;
import cn.bytes1024.hound.commons.option.DefaultConfigOption;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * @author 江浩
 */
public class AgentOptionProvider implements Provider<ConfigOption> {

    private String args;

    @Inject
    public AgentOptionProvider(@Named("args") String args) {
        this.args = args;
    }

    @Override
    public ConfigOption get() {
        return new DefaultConfigOption(this.args);
    }
}
