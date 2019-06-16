package me.stevenkin.beetle.parser;

import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;

public interface Parser<T> {

    boolean supports(Request request);

    Result<T> parse(Response response);

}
