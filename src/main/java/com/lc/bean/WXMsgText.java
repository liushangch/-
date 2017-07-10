package com.lc.bean;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 文本类型消息
 * Created by LiuChao on 2017/7/3.
 */
public class WXMsgText extends WXMsg {
    private String content;
    private String msgId;

    public WXMsgText() {
    }

    public WXMsgText(WXMsgHead head) {
        this.setHead(head);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public void read(Element element) {
        this.content = element.elementText("Content");
        this.msgId = element.elementText("MsgId");
    }

    public String toXML() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        root.addElement("ToUserName").setText(this.getHead().getFromUserName());
        root.addElement("FromUserName").setText(this.getHead().getToUserName());
        root.addElement("CreateTime").setText(String.valueOf(System.currentTimeMillis()));
        root.addElement("MsgType").setText(MSG_TYPE_TEXT);
        root.addElement("Content").setText(this.content);
        return document.asXML();
    }
}