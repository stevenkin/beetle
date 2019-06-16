package me.stevenkin.beetle.core;

import me.stevenkin.beetle.bean.Link;
import me.stevenkin.beetle.bean.Response;
import me.stevenkin.beetle.bean.Result;
import me.stevenkin.beetle.parser.AbstractPageParser;
import me.stevenkin.beetle.parser.PageParser;
import me.stevenkin.beetle.pipeline.AbstractPipeLine;
import me.stevenkin.beetle.pipeline.PipeLine;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Spider implements Runnable {
    private volatile boolean isStop = false;

    private CloseableHttpClient httpClient;

    private RequestConfig requestConfig;

    private List<AbstractPageParser> parserList;

    private List<AbstractPipeLine> pipeLineList;

    private PipeLine pipeLine;

    private LinkQueue linkQueue;

    private PageParser pageParser;

    public void init(){
        System.out.println("spider init");
        for(int i=0;i<parserList.size()-1;i++){
            parserList.get(i).setNextParser(parserList.get(i+1));
        }
        this.pageParser = parserList.get(0);
        for(int i=0;i<pipeLineList.size()-1;i++){
            pipeLineList.get(i).setNextPipeLine(pipeLineList.get(i+1));
        }
        this.pipeLine = this.pipeLineList.get(0);
    }

    @Override
    public void run() {
        Random random = new Random();
        while(!this.isStop){
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Link link = this.linkQueue.getLink();
            if(link==null)
                continue;
            if(link.isSkip()){
                continue;
            }
            String linkStr = link.getLink();
            HttpGet get = new HttpGet(linkStr);
            get.setConfig(this.requestConfig);
            try {
                HttpResponse response = httpClient.execute(get);
                if(response.getStatusLine().getStatusCode()==200) {
                    String html = EntityUtils.toString(response.getEntity(), EntityUtils.getContentCharSet(response.getEntity()));
                    Result result = pageParser.parserPage(new Response(linkStr,html));
                    if(result==null)
                        continue;
                    result.setLink(linkStr);
                    for(Link link1:result.getLinkList()){
                        linkQueue.addLink(link1);
                    }
                    pipeLine.processResult(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public List<AbstractPageParser> getParserList() {
        return parserList;
    }

    public void setParserList(List<AbstractPageParser> parserList) {
        this.parserList = parserList;
    }

    public List<AbstractPipeLine> getPipeLineList() {
        return pipeLineList;
    }

    public void setPipeLineList(List<AbstractPipeLine> pipeLineList) {
        this.pipeLineList = pipeLineList;
    }

    public LinkQueue getLinkQueue() {
        return linkQueue;
    }

    public void setLinkQueue(LinkQueue linkQueue) {
        this.linkQueue = linkQueue;
    }
}
