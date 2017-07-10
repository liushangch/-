package com.lc.bean;

import org.dom4j.Element;

/**
 * 文本类型消息
 * Created by LiuChao on 2017/7/3.
 */
public class WXMsgEvent extends WXMsg {
    public static final String SUBSCRIBE = "subscribe";
    public static final String UNSUBSCRIBE = "unsubscribe";
    public static final String CLICK = "CLICK";
    public static final String SCAN = "scan";
    public static final String LOCATION = "LOCATION";

    private String event;
    private String eventKey;
    private String ticket;
    private String latitude;
    private String longitude;
    private String precision;

    public WXMsgEvent() {
    }

    public WXMsgEvent(WXMsgHead head) {
        this.setHead(head);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public void read(Element element) {
        this.event = element.elementText("Event");
        if (!SUBSCRIBE.equals(this.event) && !UNSUBSCRIBE.equals(this.event) && !SCAN.equals(this.event)) {
            if (LOCATION.equals(this.event)) {
                this.latitude = element.elementText("Latitude");
                this.longitude = element.elementText("Longitude");
                this.precision = element.elementText("Precision");
            } else if (CLICK.equals(this.event)) {
                this.eventKey = element.elementText("EventKey");
            }
        } else {
            this.eventKey = element.elementText("EventKey");
            this.ticket = element.elementText("Ticket");
        }
    }
}
