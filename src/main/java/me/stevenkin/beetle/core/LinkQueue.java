package me.stevenkin.beetle.core;

import me.stevenkin.beetle.bean.Link;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on 2016/8/27.
 */
public class LinkQueue {
    private BlockingQueue<Link> links = new LinkedBlockingQueue<>();

    public void addLink(Link link) {
        try {
            links.put(link);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Link getLink() {
        try {
            return links.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


}
