package me.stevenkin.beetle.parser;

import me.stevenkin.beetle.response.Response;

public interface Parser<T> {

    Result<T> parse(Response response);

}
