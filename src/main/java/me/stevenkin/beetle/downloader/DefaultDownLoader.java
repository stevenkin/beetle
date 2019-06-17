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
import java.util.stream.Collectors;

@Slf4j
public class DefaultDownLoader implements DownLoader {

    private Config config;

    public DefaultDownLoader(Config config) {
        this.config = config;
    }

    @Override
    public Response download(Request request) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(config.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        list.forEach(cookie -> log.info("cookie is {}", cookie));
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        return request.cookies().stream()
                                .map(cookie -> {
                                            Cookie.Builder builder = new Cookie.Builder()
                                                    .domain(cookie.getDomain())
                                                    .expiresAt(cookie.getMaxAge())
                                                    .name(cookie.getName())
                                                    .value(cookie.getValue())
                                                    .path(cookie.getPath());
                                            if(cookie.isHttpOnly())
                                                builder.httpOnly();
                                            if(cookie.isSecure()){
                                                builder.secure();
                                            }
                                            return builder.build();
                                        })
                                .collect(Collectors.toList());
                    }
                }).build();
        String method = request.method();
        String url = request.url();
        Multimap<String, String> params = request.params();
        Map<String, String> headers = request.headers();
        okhttp3.Request request1;
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        switch (method){
            case "get":
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
            case "post":
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
