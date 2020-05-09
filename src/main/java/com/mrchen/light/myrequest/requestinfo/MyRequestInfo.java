package com.mrchen.light.myrequest.requestinfo;


/**
 * @program: wechat-demo
 * @description:
 * @author: mrchen
 * @create: 2020-05-07 17:25
 */
public class MyRequestInfo {
    private String url;
    private MyRequestMethod requestMethod;
    private String requestParams;

    public MyRequestInfo(String url, MyRequestMethod requestMethod,String requestParams) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.requestParams=requestParams;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MyRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(MyRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }
}
