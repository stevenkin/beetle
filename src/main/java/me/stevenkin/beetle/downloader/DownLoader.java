package me.stevenkin.beetle.downloader;

import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;

public interface DownLoader {

    Response download(Request request);

}
