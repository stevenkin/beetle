package me.stevenkin.beetle.pipeline;

import me.stevenkin.beetle.bean.Result;

/**
 * Created by wjg on 16-9-8.
 */
public abstract class AbstractPipeLine implements PipeLine {
    private PipeLine nextPipeLine;

    @Override
    public void processResult(Result result) {
        if(checkProcess(result)){
            process(result);
        }
        if(this.nextPipeLine!=null)
            this.nextPipeLine.processResult(result);
    }

    public abstract void process(Result result);

    public PipeLine getNextPipeLine() {
        return nextPipeLine;
    }

    public void setNextPipeLine(PipeLine nextPipeLine) {
        this.nextPipeLine = nextPipeLine;
    }
}
