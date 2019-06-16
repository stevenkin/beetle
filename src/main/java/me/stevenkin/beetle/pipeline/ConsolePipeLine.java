package me.stevenkin.beetle.pipeline;

import me.stevenkin.beetle.bean.Result;


public class ConsolePipeLine extends AbstractPipeLine {
    @Override
    public void process(Result result) {
        for(Object content : result.getContentList()){
            System.out.println(content);
        }
    }

    @Override
    public boolean checkProcess(Result result) {
        return true;
    }
}
