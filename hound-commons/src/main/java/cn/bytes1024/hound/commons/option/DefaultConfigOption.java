package cn.bytes1024.hound.commons.option;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author 江浩
 */
@Slf4j
public class DefaultConfigOption extends Properties implements ConfigOption {

    private static final String DEFAULT = "agent.properties";

    private String path;

    public DefaultConfigOption(String path) {
        this.path = path;
        this.load(this.path);
    }

    @Override
    public void load(String path) {
        try {
            InputStream inputStream =
                    readFile(path) ? new FileInputStream(path) :
                            this.getClass().getClassLoader().getResourceAsStream(DEFAULT);

            this.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean readFile(String path) {
        return StringUtils.isNotBlank(path);
    }

//    @Override
//    public String getAgentId() {
//        return this.getProperty("bytes.hound.agent.id", "");
//    }
//
//    @Override
//    public String getTransfer() {
//        return this.getOption("bytes.hound.transfer.type", "");
//    }
//
//    @Override
//    public String getTracerType() {
//        return this.getOption("bytes.hound.tracer.type", UUID.randomUUID().toString());
//    }


    @Override
    public String getOption(String key, String defaultValue) {
        return this.getProperty(key, defaultValue);
    }

    @Override
    public List<String> getOptions(String key) {
        String values = this.getOption(key, null);
        try {
            return StringUtils.isBlank(values) ? new ArrayList<>() :
                    Arrays.asList(StringUtils.split(values, ","));
        } catch (Exception e) {
            log.error("handle key {} error values {}", key, values);
            return null;
        }
    }


}
