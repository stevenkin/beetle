package me.stevenkin.beetle.kit;

import lombok.extern.slf4j.Slf4j;
import me.stevenkin.beetle.config.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@Slf4j
public class ConfigKit {

    public static Config config(String configFile){
        URL url = ConfigKit.class.getClassLoader().getResource("extObj.txt");
        File file = new File(url.getFile());
        if(!file.exists())
            return null;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("config file load error");
        }
        Config config = new Config();
        config.setDelay(Integer.parseInt(properties.getProperty("delay")));
        config.setParallelThreads(Integer.parseInt(properties.getProperty("parallelThreads")));
        config.setQueueSize(Integer.parseInt(properties.getProperty("queueSize")));
        config.setTimeout(Integer.parseInt(properties.getProperty("timeout")));
        config.setUserAgent(properties.getProperty("userAgent"));
        return config;
    }

}
