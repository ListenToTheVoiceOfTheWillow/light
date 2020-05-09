package com.mrchen.light.test;

import com.mrchen.light.myrequest.handler.support.MethodHandler;

/**
 * @program: wechat-demo
 * @description:
 * @author: mrchen
 * @create: 2020-05-08 08:49
 */
public class TestMethodHandler extends MethodHandler {
    @Override
    public Object doRequest() {
        return HttpRequestUtil.httpRequest(getMyRequestInfo().getUrl(),getMyRequestInfo().getRequestMethod().name(),getMyRequestInfo().getRequestParams());
    }
}
