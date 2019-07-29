package cn.bytes1024.hound.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationServer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(ApplicationServer.class).run(args);
        context.addApplicationListener(new ApplicationPidFileWriter());
        context.addApplicationListener(new WebServerPortFileWriter());
    }
}