package me.stevenkin.beetle.parser;

import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;

public interface Parser {

    boolean supports(Request request);

    Result parse(Response response);

}
