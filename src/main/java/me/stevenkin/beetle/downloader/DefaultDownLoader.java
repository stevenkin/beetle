package me.stevenkin.beetle.downloader;

import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import me.stevenkin.beetle.config.Config;
import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultDownLoader implements DownLoader {

    private OkHttpClient client;

    private Config config;

    private DefaultCookiePool cookiePool;

    public DefaultDownLoader(Config config) {
        this.config = config;
        this.cookiePool = new DefaultCookiePool();
        this.client = new OkHttpClient.Builder()
                .connectTimeout(config.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        list.forEach(cookie -> log.info("get cookie is {}", cookie));
                        cookiePool.saveCookies(list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        return cookiePool.loadCookies(httpUrl.host());
                    }
                }).build();
    }

    @Override
    public Response download(Request request) {
        String method = request.method();
        String url = request.url();
        Multimap<String, String> params = request.params();
        Map<String, String> headers = request.headers();
        okhttp3.Request request1;
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        switch (method){
            case "GET":
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(url);
                stringBuilder.append("?");
                params.entries().forEach(
                        entry->stringBuilder
                                .append(entry.getKey())
                                .append("=")
                                .append(entry.getValue())
                                .append("&")
                );
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                builder.url(url);
                break;
            case "POST":
                builder.url(url);
                okhttp3.FormBody.Builder builder1 = new okhttp3.FormBody.Builder();
                params.entries().forEach(e->builder1.add(e.getKey(), e.getValue()));
                builder.post(builder1.build());
                break;
            default:
                throw new RuntimeException("no supports method");
        }

        headers.entrySet().forEach(e->builder.addHeader(e.getKey(), e.getValue()));
        request1 = builder.build();
        try {
            okhttp3.Response response = client.newCall(request1).execute();
            return new Response(request, response.body().bytes());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("request {} io error",request);
            return null;
        }

    }
}
