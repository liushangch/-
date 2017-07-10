package com.lc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信接口
 * Created by LiuChao on 2017/7/7.
 */
@Controller
@RequestMapping("/wx")
public class WeChatController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        log.info("微信接口");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (null != signature && null != timestamp && null != nonce && null != echostr) {/* 接口验证  */
            log.info("验证参数：[signature:" + signature + "][timestamp:" + timestamp + "][nonce:" + nonce + "][echostr:" + echostr + "]");
//            return wxMpRequestServer.validationSource(signature, timestamp, nonce, echostr);
        } else {
//            return wxMpRequestServer.processRequest(request);
        }
        return "";
    }
}
