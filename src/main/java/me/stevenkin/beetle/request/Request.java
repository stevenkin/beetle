package me.stevenkin.beetle.request;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String url;
    private String method  = "GET";
    private Multimap<String, String> params = LinkedListMultimap.create();
    private Map<String, String> headers = new HashMap<>();
    private List<Cookie> cookies = new ArrayList<>();
    private String contentType = "text/html; charset=UTF-8";
    private String charset = "UTF-8";

    public String url(){
        return this.url;
    }

    public Request url(String url){
        this.url = url;
        return this;
    }

    public Request param(String key, String value){
        this.params.put(key, value);
        return this;
    }

    public Multimap<String, String> params(){
        return this.params;
    }

    public Request header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public Request cookie(Cookie cookie) {
        this.cookies.add(cookie);
        return this;
    }

    public String header(String key) {
        return this.headers.get(key);
    }

    public Map<String, String> headers(){
        return this.headers;
    }

    public List<Cookie> cookies() {
        return this.cookies;
    }

    public String contentType() {
        return contentType;
    }

    public Request contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String charset() {
        return charset;
    }

    public Request charset(String charset) {
        this.charset = charset;
        return this;
    }

    public Request method(String method) {
        this.method = method;
        return this;
    }

    public String method() {
        return this.method;
    }




}
