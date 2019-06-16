package me.stevenkin.beetle.parser;

import me.stevenkin.beetle.bean.Response;
import me.stevenkin.beetle.bean.Result;

/**
 * Created by Administrator on 2016/8/27.
 */
public interface PageParser {
    public boolean checkParser(Response response);

    public Result parserPage(Response response);
}
