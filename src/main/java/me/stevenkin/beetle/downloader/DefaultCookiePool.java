package me.stevenkin.beetle.downloader;

import okhttp3.Cookie;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class DefaultCookiePool implements CookiePool {

    private List<Cookie> cookieList = new CopyOnWriteArrayList<>();

    @Override
    public void saveCookies(List<Cookie> cookies) {
        this.cookieList.addAll(cookies);
    }

    @Override
    public List<Cookie> loadCookies(String url) {
        return this.cookieList.stream()
                .filter(cookie -> url.contains(cookie.domain()))
                .collect(Collectors.toList());
    }
}
