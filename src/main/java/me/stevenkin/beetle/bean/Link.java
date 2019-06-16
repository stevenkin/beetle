package me.stevenkin.beetle.bean;

public class Link {
    private String link;
    private boolean isSkip;

    public Link(String link, boolean isSkip) {
        this.link = link;
        this.isSkip = isSkip;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isSkip() {
        return isSkip;
    }

    public void setIsSkip(boolean isSkip) {
        isSkip = isSkip;
    }
}
