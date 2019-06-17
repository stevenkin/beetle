package me.stevenkin.beetle;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.stevenkin.beetle.config.Config;
import me.stevenkin.beetle.downloader.DefaultDownLoader;
import me.stevenkin.beetle.downloader.DownLoader;
import me.stevenkin.beetle.kit.NamedThreadFactory;
import me.stevenkin.beetle.parser.Parser;
import me.stevenkin.beetle.parser.Result;
import me.stevenkin.beetle.pipeline.PipeLine;
import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;
import me.stevenkin.beetle.scheduler.DefaultScheduler;
import me.stevenkin.beetle.scheduler.Scheduler;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class BeetleEngine {
    private DownLoader downLoader;
    private Scheduler scheduler;
    private Config config;
    @Setter
    private List<Parser> parsers;
    @Setter
    private List<PipeLine> pipeLines;
    @Setter
    private List<Request> startRequest;
    private ExecutorService executorService;

    private boolean isRunning;

    public BeetleEngine(Config config) {
        this.downLoader = new DefaultDownLoader(config);
        this.scheduler = new DefaultScheduler();
        this.config = config;
        this.executorService = new ThreadPoolExecutor(config.getParallelThreads(), config.getParallelThreads(), 0, TimeUnit.MILLISECONDS,
                config.getQueueSize() == 0 ? new SynchronousQueue<>()
                        : (config.getQueueSize() < 0 ? new LinkedBlockingQueue<>()
                        : new LinkedBlockingQueue<>(config.getQueueSize())), new NamedThreadFactory("task"));
        this.isRunning = false;
    }

    public void start(){
        if(isRunning)
            throw new RuntimeException("already started");
        isRunning = true;
        this.startRequest.forEach(request -> scheduler.addRequest(request));

        Thread downloadThread = new Thread(() ->{
            while(isRunning){
                if(!scheduler.hasRequest()){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        log.error("error is {}",e);
                    }
                    continue;
                }
                Request request = scheduler.takeRequest();
                executorService.submit(() ->
                    scheduler.addResponse(downLoader.download(request))
                );
                try {
                    Thread.sleep(config.getDelay() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error("error is {}",e);
                }
            }
        });
        downloadThread.setDaemon(true);
        downloadThread.setName("download-thread");
        downloadThread.start();
        parse();
    }

    private void parse(){
        while(isRunning){
            if(!scheduler.hasResponse()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error("error is {}",e);
                }
                continue;
            }
            Response response = scheduler.takeResponse();
            Parser parser = null;
            for (Parser parser1 : parsers){
                if(parser1.supports(response.getRequest())){
                    parser = parser1;
                    break;
                }
            }
            if(parser == null){
                log.warn("the response {} can not find a parser", response);
                continue;
            }
            Result result = parser.parse(response);
            result.getRequests().forEach(request -> scheduler.addRequest(request));
            pipeLines.forEach(pipeLine -> pipeLine.process(result.getItem(), response.getRequest()));
        }
    }

    public void stop(){
        isRunning = false;
    }

}
