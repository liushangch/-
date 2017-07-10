package com.lc.service;


import com.lc.bean.*;
import com.lc.util.PropertiesUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 微信公众平台授权相关接口实现
 * Created by LiuChao on 2017/5/24.
 */
@Service("wxMpRequestServer")
public class WXMpRequestServer {
    private Logger log = LoggerFactory.getLogger(WXMpRequestServer.class);
    private static String appId;
    private static String appSecret;
    private static String serviceToken;

    static {
        appId = PropertiesUtil.getConfig().getString("wx.mp.people.AppId");
        appSecret = PropertiesUtil.getConfig().getString("wx.mp.people.AppSecret");
        serviceToken = PropertiesUtil.getConfig().getString("wx.mp.people.serviceToken");
    }

    public String validationSource(String signature, String timestamp, String nonce, String echostr) {
        log.info("验证消息是否来自微信服务器");
        List<String> list = new ArrayList<>();
        list.add(nonce);
        list.add(timestamp);
        list.add(serviceToken);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        list.forEach(sb::append);
        String signatureNow = DigestUtils.sha1Hex(sb.toString());
        if (signatureNow.equals(signature)) {
            return echostr;
        } else {
            return "";
        }
    }

    public String processRequest(HttpServletRequest request) {
        log.info("微信消息处理");
        String xml = "";
        try {
            InputStream is = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();
            WXMsgHead head = new WXMsgHead();
            head.read(root);
            String type = head.getMsgType();
            log.info("---------->" + type);
            switch (type) {
                case WXMsg.MSG_TYPE_TEXT:
                    WXMsgText msgText = new WXMsgText(head);
                    msgText.read(root);
                    // 根据数据库查询匹配的关键字
                    boolean flag = false;
                    // 回复文字消息
                    if (flag) {
                        msgText.setContent("回复文字消息");
                        xml = msgText.toXML();
                    } else {
                        // 回复图文消息
                        WXMsgImageText msgImageText = new WXMsgImageText(head);
                        head.setMsgType(WXMsg.MSG_TYPE_IMAGE_TEXT);
                        xml = msgImageText.toXML();
                    }
                    break;
                case WXMsg.MSG_TYPE_IMAGE:
                case WXMsg.MSG_TYPE_VOICE:
                case WXMsg.MSG_TYPE_VIDEO:
                case WXMsg.MSG_TYPE_SHORTVIDEO:
                case WXMsg.MSG_TYPE_LOCATION:
                case WXMsg.MSG_TYPE_LINK:
                    msgText = new WXMsgText(head);
                    msgText.read(root);
                    msgText.setContent("您的小益正在赶来，请稍等！");
                    xml = msgText.toXML();
                    break;
                case WXMsg.MSG_TYPE_EVENT:
                    WXMsgEvent msgEvent = new WXMsgEvent(head);
                    msgEvent.read(root);
                    // 用于消息发送
                    msgText = new WXMsgText(head);
                    log.info("---------->" + msgEvent.getEvent());
                    switch (msgEvent.getEvent()) {
                        case WXMsgEvent.SUBSCRIBE:
                            msgText.setContent("");
                            xml = msgText.toXML();
                            break;
                        case WXMsgEvent.CLICK:
                            xml = msgText.toXML();
                            break;
                        default:
                            xml = "";
                            break;
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("失败", e);
            xml = "";
        }
        log.info("---------->" + xml);
        return xml;
    }

//    public ServiceResponse<String> loginWX(String json) {
//        log.info("服务号用户登录系统");
//        ServiceResponse<String> response = new ServiceResponse<>();
//        try {
//            JSONObject in = JSON.parseObject(json);
//            String wxCode = in.getString("wxCode");
//            JSONObject accessTokenObject = getAccessToken(appId, appSecret, wxCode);
//            if (accessTokenObject == null) {
//                log.error("微信：获取accessToken失败");
//                return new ServiceResponse<>(ServiceResponse.STATUS_FAILED, "微信：获取accessToken失败");
//            }
//            String openId = accessTokenObject.getString("openid");
//            JSONObject userInfo = getUserInfo(WXInitService.accessToken, openId);
//            if (userInfo == null) {
//                log.error("微信：获取用户信息失败");
//                return new ServiceResponse<>(ServiceResponse.STATUS_FAILED, "微信：获取用户信息失败");
//            }
//            // 根据unionId查询用户
//            String unionId = userInfo.getString("unionid");
//            PageData pd = new PageData();
//            pd.put("unionid", unionId);
//            pd.put("openid", openId);
//            pd = peopleInfoService.findByOpenIdOrUnionId(pd);
//            if (pd != null) {
//                RedisLogin bean = new RedisLogin();
//                bean.setId(pd.getString("id"));
//                bean.setName(pd.getString("phone"));
//                bean.setTokenWx(TokenUtil.getToken(pd.getString("phone") + "," + pd.getString("id")));
//                bean.setSecretKeyWx(in.getString("secretKey"));
//                bean.setLastLoginTime(new Date());
//                redisLoginDao.insert(bean, Constants.PEOPLE);
//                response.setValue(bean.getTokenWx());
////                // 登录成功，发送微信消息
////                String text = "您已登录成功！";
////                TextMessage textMessage = new TextMessage(openId, text);
////                wxSendMessage.sendMessage(textMessage);
//            } else {
//                response.setValue("false" + "," + openId);
//            }
//        } catch (Exception e) {
//            log.error("服务号用户登录系统失败", e);
//            return new ServiceResponse<>(ServiceResponse.STATUS_FAILED, "服务号用户登录系统失败");
//        }
//        return response;
//    }
//
//    public ServiceResponse<String> loginWXByPhone(String json) {
//        log.info("服务号用户和系统用户通过手机绑定并登录系统");
//        ServiceResponse<String> response = new ServiceResponse<>();
//        try {
//            JSONObject in = JSON.parseObject(json);
//            String phone = in.getString("phone");
//            String code = in.getString("code");
//            String openId = in.getString("openId");
//            // 验证码校验
//            RedisPhoneCode redisPhoneCode = redisPhoneCodeDao.selectForLoginById(phone, Constants.PEOPLE);
//            if ((redisPhoneCode == null || !code.equals(redisPhoneCode.getCode()))) {
//                log.error("验证码不匹配");
//                return new ServiceResponse<>(ServiceResponse.STATUS_FAILED, "验证码不匹配");
//            }
//            JSONObject userInfo = getUserInfo(WXInitService.accessToken, openId);
//            if (userInfo == null) {
//                log.error("微信：获取用户信息失败");
//                return new ServiceResponse<>(ServiceResponse.STATUS_FAILED, "微信：获取用户信息失败");
//            }
//            // 根据phone查询用户
//            String unionId = userInfo.getString("unionid");
//            PageData pd = new PageData();
//            pd.put("phone", phone);
//            pd = peopleInfoService.findByPhone(pd);
//            if (pd == null) {
//                // 新建游客居民
//                pd = new PageData();
//                pd.put("id", CommonUtil.nextId());
//                pd.put("phone", phone);
//                pd.put("apply_state", Constants.APPLY_00.key);
//                pd.put("create_time", Tools.date2Str(new Date()));
//                pd.put("del", 0);
//                pd.put("openid", openId);
//                pd.put("unionid", unionId);
//                peopleInfoService.save(pd);
//            } else {
//                pd.put("openid", openId);
//                pd.put("unionid", unionId);
//                peopleInfoService.edit(pd);
//            }
//            RedisLogin bean = new RedisLogin();
//            bean.setId(pd.getString("id"));
//            bean.setName(pd.getString("phone"));
//            bean.setTokenWx(TokenUtil.getToken(pd.getString("phone") + "," + pd.getString("id")));
//            bean.setSecretKeyWx(in.getString("secretKey"));
//            bean.setLastLoginTime(new Date());
//            redisLoginDao.insert(bean, Constants.PEOPLE);
//            // 登录成功，发送微信消息
//            String text = "您已登录成功！";
//            TextMessage textMessage = new TextMessage(openId, text);
//            wxSendMessage.sendMessage(textMessage);
//            response.setValue(bean.getTokenWx());
//        } catch (Exception e) {
//            log.error("服务号用户和系统用户通过手机绑定并登录系统失败" + e);
//            return new ServiceResponse<>(ServiceResponse.STATUS_FAILED, "服务号用户和系统用户通过手机绑定并登录系统失败");
//        }
//        return response;
//    }
//
//    @Override
//    public ServiceResponse<OutJSSDKInfo> getSignature(String json) {
//        OutJSSDKInfo out = new OutJSSDKInfo();
//        log.info("JS-SDK获取签名");
//        ServiceResponse<OutJSSDKInfo> response = new ServiceResponse<>();
//        try {
//            JSONObject in = JSON.parseObject(json);
//            String url = in.getString("url");
//            out.setNonceStr(CommonUtil.getRandomString(16));
//            out.setTimestamp(String.valueOf(System.currentTimeMillis()));
//            String str = "jsapi_ticket=" + WXInitService.jsapiTicket
//                    + "&noncestr=" + out.getNonceStr()
//                    + "&timestamp=" + out.getTimestamp()
//                    + "&url=" + url;
//            out.setSignature(DigestUtils.shaHex(str));
//            response.setValue(out);
//        } catch (Exception e) {
//            log.error("JS-SDK获取签名失败" + e);
//            return new ServiceResponse<>(ServiceResponse.STATUS_FAILED, "JS-SDK获取签名失败");
//        }
//        return response;
//    }
}
