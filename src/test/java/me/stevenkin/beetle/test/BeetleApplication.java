package me.stevenkin.beetle.test;

import lombok.extern.slf4j.Slf4j;
import me.stevenkin.beetle.Beetle;
import me.stevenkin.beetle.config.Config;
import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.test.bean.Blog;
import me.stevenkin.beetle.test.parser.SegmentfaultParser;

import java.util.List;

@Slf4j
public class BeetleApplication {
    public static void main(String[] args) {
        Beetle.me(Config.me())
                .parser(new SegmentfaultParser())
                .pipeLine((item, request) -> ((List<Blog>)item).forEach(blog -> log.info("get blog {}", blog)))
                .startRequest(new Request().url("https://segmentfault.com/blogs/newest"))
                .start();
    }
}
