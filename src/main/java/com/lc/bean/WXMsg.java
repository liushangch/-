package com.lc.bean;

import org.dom4j.Element;

/**
 * 微信消息类型
 * Created by LiuChao on 2017/7/3.
 */
public class WXMsg {
    public static final String MSG_TYPE_TEXT = "text";
    public static final String MSG_TYPE_IMAGE = "image";
    public static final String MSG_TYPE_MUSIC = "music";
    public static final String MSG_TYPE_LOCATION = "location";
    public static final String MSG_TYPE_LINK = "link";
    public static final String MSG_TYPE_IMAGE_TEXT = "news";
    public static final String MSG_TYPE_EVENT = "event";
    public static final String MSG_TYPE_VOICE = "voice";
    public static final String MSG_TYPE_VIDEO = "video";
    public static final String MSG_TYPE_SHORTVIDEO = "shortvideo";

    private WXMsgHead head = new WXMsgHead();

    public WXMsgHead getHead() {
        return head;
    }

    public void setHead(WXMsgHead head) {
        this.head = head;
    }

    public void read(Element element) {
    }
}
