package me.stevenkin.beetle.scheduler;

import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;

public interface Scheduler {

    void addRequest(Request request);

    void addResponse(Response response);

    Request takeRequest();

    Response takeResponse();

    boolean hasRequest();

    boolean hasResponse();

    void clear();
}
