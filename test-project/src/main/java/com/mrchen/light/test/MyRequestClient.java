package com.mrchen.light.test;


import com.mrchen.light.myrequest.annotation.MyParam;
import com.mrchen.light.myrequest.annotation.MyRequestLine;
import net.sf.json.JSONObject;

/**
 * @program: wechat-demo
 * @description: 测试
 * @author: mrchen
 * @create: 2020-05-07 16:36
 */
public interface MyRequestClient {

    /**
     * 得到 access_token
     * @param cor_pid
     * @param cor_secret
     * @return
     */
    @MyRequestLine(path = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpid}&corpsecret={corpsecret}")
    JSONObject getAccess_token(@MyParam("corpid") String cor_pid, @MyParam("corpsecret") String cor_secret);

}
