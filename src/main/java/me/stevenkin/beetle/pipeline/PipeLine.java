package me.stevenkin.beetle.pipeline;

import me.stevenkin.beetle.request.Request;

@FunctionalInterface
public interface PipeLine {

    void process(Object item, Request request);


}
