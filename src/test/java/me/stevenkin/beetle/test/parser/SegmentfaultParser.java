package me.stevenkin.beetle.test.parser;

import me.stevenkin.beetle.parser.Parser;
import me.stevenkin.beetle.parser.Result;
import me.stevenkin.beetle.request.Request;
import me.stevenkin.beetle.response.Response;
import me.stevenkin.beetle.test.bean.Blog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SegmentfaultParser implements Parser{
    private static final String BASIC_URL = "https://segmentfault.com";

    @Override
    public boolean supports(Request request) {
        return request.url().contains("segmentfault.com");
    }

    @Override
    public Result parse(Response response) {
        byte[] body = response.getBody();
        String html = new String(body);
        List<Blog> blogs = new ArrayList<>();
        Result result = new Result();
        Document document = Jsoup.parse(html);
        Elements sections = document.select("section.stream-list__item");
        for(Element section:sections){
            String title = section.select("a[href]").text();
            String link = BASIC_URL+section.select("a[href").attr("href");
            String resume = section.select("p.excerpt.wordbreak.hidden-xs").text();
            Blog blog = new Blog();
            blog.setLink(link);
            blog.setResume(resume);
            blog.setTitle(title);
            blogs.add(blog);
        }
        Element nextElem = document.select("li.next a").first();
        if(nextElem!=null) {
            String nextLink = BASIC_URL+nextElem.attr("href");
            result.addRequest(new Request().url(nextLink));
        }
        result.setItem(blogs);
        return result;
    }
}
