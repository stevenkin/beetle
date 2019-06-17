package me.stevenkin.beetle.downloader;

import okhttp3.Cookie;

import java.util.List;

public interface CookiePool {
    void saveCookies(List<Cookie> cookies);

    List<Cookie> loadCookies(String host);
}
