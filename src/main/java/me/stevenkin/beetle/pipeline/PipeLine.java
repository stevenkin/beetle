package me.stevenkin.beetle.pipeline;

import me.stevenkin.beetle.bean.Result;

/**
 * Created by Administrator on 2016/8/27.
 */
public interface PipeLine {
    public boolean checkProcess(Result result);
    public void processResult(Result result);
}
