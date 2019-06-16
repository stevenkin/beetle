package me.stevenkin.beetle.pipeline;

import me.stevenkin.beetle.request.Request;

@FunctionalInterface
public interface PipeLine<T> {

    void process(T item, Request request);


}
