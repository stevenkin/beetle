package me.stevenkin.beetle;

import me.stevenkin.beetle.config.Config;
import me.stevenkin.beetle.parser.Parser;
import me.stevenkin.beetle.pipeline.PipeLine;
import me.stevenkin.beetle.request.Request;

import java.util.ArrayList;
import java.util.List;

public class Beetle {

    private BeetleEngine beetleEngine;

    private List<Parser> parsers;

    private List<PipeLine> pipeLines;

    private List<Request> startRequests;

    private Config config;

    private Beetle(Config config) {
        this.config = config;
        this.beetleEngine = new BeetleEngine(config);
        this.parsers = new ArrayList<>();
        this.pipeLines = new ArrayList<>();
        this.startRequests = new ArrayList<>();
    }

    public Beetle parser(Parser parser){
        this.parsers.add(parser);
        return this;
    }

    public Beetle pipeLine(PipeLine pipeLine){
        this.pipeLines.add(pipeLine);
        return this;
    }

    public Beetle startRequest(Request request){
        this.startRequests.add(request);
        return this;
    }

    public Beetle start(){
        this.beetleEngine.setParsers(parsers);
        this.beetleEngine.setPipeLines(pipeLines);
        this.beetleEngine.setStartRequest(startRequests);
        this.beetleEngine.start();
        return this;
    }

    public void stop(){
        this.beetleEngine.stop();
    }

    public static Beetle me(Config config){
        return new Beetle(config);
    }
}
