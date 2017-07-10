package com.lc.bean;

/**
 * 图文
 * Created by LiuChao on 2017/7/4.
 */
public class WXArticle {

    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;

    public WXArticle() {
    }

    public WXArticle(String title, String description, String picUrl, String url) {
        Title = title;
        Description = description;
        PicUrl = picUrl;
        Url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "WXArticle{" +
                "Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", PicUrl='" + PicUrl + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
