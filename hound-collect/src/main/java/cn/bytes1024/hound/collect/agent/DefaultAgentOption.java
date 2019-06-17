package cn.bytes1024.hound.collect.agent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.Properties;
import java.util.UUID;

/**
 * @author 江浩
 */
@Slf4j
public class DefaultAgentOption extends Properties implements AgentOption {

    private static final String DEFAULT = "agent.properties";

    private String path;

    public DefaultAgentOption(String path) {
        this.path = path;
        this.load(this.path);
    }

    @Override
    public void load(String path) {

        try {

            InputStream inputStream =
                    readFile(path) ? new FileInputStream(path):
                            this.getClass().getClassLoader().getResourceAsStream(DEFAULT);

            this.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("load config path: {} error",path);
        }
    }

    private boolean readFile(String path) {
        return StringUtils.isNotBlank(path);
    }

    @Override
    public String getAgentId() {
        return this.getProperty("bytes.hound.agent.id","");
    }

    @Override
    public String getTransfer() {
        return this.getOption("bytes.hound.transfer.name","");
    }

    @Override
    public String getTracerType() {
        return this.getOption("bytes.hound.tracer.type", UUID.randomUUID().toString());
    }


    @Override
    public String getOption(String key, String defaultValue) {
        return this.getProperty(key,defaultValue);
    }



}
