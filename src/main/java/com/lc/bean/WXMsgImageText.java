package com.lc.bean;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文类型消息
 * Created by LiuChao on 2017/7/3.
 */
public class WXMsgImageText extends WXMsg {
    private String articleCount;
    private List<WXArticle> items = new ArrayList<>();

    public WXMsgImageText() {
    }

    public WXMsgImageText(WXMsgHead head) {
        this.setHead(head);
    }

    public String getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(String articleCount) {
        this.articleCount = articleCount;
    }

    public List<WXArticle> getItems() {
        return items;
    }

    public void setItems(List<WXArticle> items) {
        this.items = items;
    }

    public void addItem(WXArticle item) {
        this.items.add(item);
        this.articleCount = String.valueOf(items.size());
    }

    public String toXML() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        root.addElement("ToUserName").setText(this.getHead().getFromUserName());
        root.addElement("FromUserName").setText(this.getHead().getToUserName());
        root.addElement("CreateTime").setText(String.valueOf(System.currentTimeMillis()));
        root.addElement("MsgType").setText(MSG_TYPE_TEXT);
        root.addElement("ArticleCount").setText(this.articleCount);
        Element articles = root.addElement("Articles");
        articles.setText(this.articleCount);
        for (WXArticle article : items) {
            Element item = articles.addElement("item");
            item.setText(article.getTitle());
            item.addElement("Title").setText(article.getTitle());
            item.addElement("Description").setText(article.getDescription());
            item.addElement("PicUrl").setText(article.getPicUrl());
            item.addElement("Url").setText(article.getUrl());
        }
        return document.asXML();
    }
}
