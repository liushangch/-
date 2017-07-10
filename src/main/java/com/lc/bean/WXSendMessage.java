package com.lc.bean;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.ihealthtek.ed.service.init.WXInitService;
//import com.ihealthtek.ed.service.request.WXHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送模板消息
 * Created by LiuChao on 2017/6/30.
 */
@Service
public class WXSendMessage {
    private static Logger log = LoggerFactory.getLogger(WXSendMessage.class);
//    @Autowired
//    private WXHttpRequest wxHttpRequest;

//    /**
//     * 发送文本消息
//     */
//    public boolean sendMessage(TextMessage textMessage) {
//        log.info("发送文本消息" + textMessage.toJson());
//        String uri = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + WXInitService.accessToken;
//        String body = wxHttpRequest.postRequest(uri, textMessage.toJson());
//        JSONObject jsonObject = JSON.parseObject(body);
//        if ("0".equals(jsonObject.getString("errcode"))) {
//            return true;
//        } else {
//            log.error("发送文本消息失败：" + jsonObject.getString("errcode") + "_" + jsonObject.getString("errmsg"));
//            return false;
//        }
//    }
//
//    /**
//     * 发送模板消息
//     */
//    public boolean sendMessage(Template template) {
//        log.info("发送模板消息" + template.toString());
//        String uri = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WXInitService.accessToken;
//        String body = wxHttpRequest.postRequest(uri, template.toJson());
//        JSONObject jsonObject = JSON.parseObject(body);
//        if ("0".equals(jsonObject.getString("errcode"))) {
//            return true;
//        } else {
//            log.error("发送模板消息失败：" + jsonObject.getString("errmsg"));
//            return false;
//        }
//    }
}
