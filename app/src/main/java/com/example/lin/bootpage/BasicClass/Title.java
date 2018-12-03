package com.example.lin.bootpage.BasicClass;

public class Title {
    private String title;
    private String time;
    private String imageUrl;
    private String uri;

    public Title(String title,String time, String imageUrl, String uri){
        this.title = title;
        this.imageUrl = imageUrl;
        this.time =time;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTime() {
        return time;
    }

    public String getUri() {
        return uri;
    }
}
