package com.example.lin.bootpage.BasicClass;

import org.litepal.crud.DataSupport;

public class Collection extends DataSupport {
    private String name;
    private String newsTitle;
    private String newsUrl;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
