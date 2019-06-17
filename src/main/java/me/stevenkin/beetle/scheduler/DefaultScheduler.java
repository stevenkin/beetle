package me.stevenkin.beetle.scheduler;

import lombok.extern.slf4j.Slf4j;
import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class DefaultScheduler implements Scheduler {

    private BlockingQueue<Request> pending = new LinkedBlockingQueue<>();
    private BlockingQueue<Response> result  = new LinkedBlockingQueue<>();

    @Override
    public void addRequest(Request request) {
        this.pending.add(request);
    }

    @Override
    public void addResponse(Response response) {
        this.result.add(response);
    }

    @Override
    public Request takeRequest() {
        try {
            return this.pending.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("take request error");
            return null;
        }
    }

    @Override
    public Response takeResponse() {
        try {
            return this.result.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("take response error");
            return null;
        }
    }

    @Override
    public boolean hasRequest() {
        return this.pending.size() > 0;
    }

    @Override
    public boolean hasResponse() {
        return this.result.size() > 0;
    }

    @Override
    public void clear() {
        this.pending.clear();
        this.result.clear();
    }
}
