package me.stevenkin.beetle.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.stevenkin.beetle.kit.ConfigKit;

@Data
@NoArgsConstructor
public class Config {
    /**
     * 连接超时设置
     */
    private int timeout = 30;

    /**
     * 读取超时设置
     */
    private int readTimeout = 10;

    /**
     * 下载间隔
     */
    private int delay = 1;

    /**
     * 下载线程数
     */
    private int parallelThreads = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * UserAgent
     */
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11";

    /**
     * 队列大小
     */
    private int queueSize = -1;

    public static Config me(){
        Config config = ConfigKit.config("beetle.properties");
        return config == null ? new Config() : config;
    }
}
