package com.mrchen.light.myrequest.handler.support;



import com.mrchen.light.myrequest.handler.Handler;
import com.mrchen.light.myrequest.requestinfo.MyRequestInfo;

import java.lang.reflect.Method;

/**
 * @program: wechat-demo
 * @description:
 * @author: mrchen
 * @create: 2020-05-07 17:24
 */
public abstract class MethodHandler implements Handler {
    private Method method;

    private MyRequestInfo myRequestInfo;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


    public MyRequestInfo getMyRequestInfo() {
        return myRequestInfo;
    }

    public void setMyRequestInfo(MyRequestInfo myRequestInfo) {
        this.myRequestInfo = myRequestInfo;
    }
}
