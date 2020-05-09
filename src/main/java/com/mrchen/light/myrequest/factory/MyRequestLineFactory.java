package com.mrchen.light.myrequest.factory;
import com.mrchen.light.myrequest.annotation.MyRequestLine;
import com.mrchen.light.myrequest.handler.support.MethodHandler;
import com.mrchen.light.myrequest.proxy.RequestJdkProxy;
import com.mrchen.light.myrequest.requestinfo.MyRequestInfo;
import com.mrchen.light.myrequest.requestinfo.MyRequestMethod;
import com.mrchen.light.myrequest.utils.ReflectUtils;
import com.mrchen.light.myrequest.utils.TextUtil;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: wechat-demo
 * @description:
 * @author: mrchen
 * @create: 2020-05-07 16:56
 */
public class MyRequestLineFactory {

    private static MyRequestLineFactory myRequestLineFactory=null;

    public static MyRequestLineFactory getInstance(){
          if (myRequestLineFactory==null){
              synchronized (MyRequestLineFactory.class) {
                  if (myRequestLineFactory==null) {
                      myRequestLineFactory = new MyRequestLineFactory();
                  }
              }
          }
          return myRequestLineFactory;
    }
    public  <T> T create(Class<?> requestClientClazz,Class<?> handlerClazz) {
        List<MethodHandler> handlers=new ArrayList<>();
        Method[] methods= requestClientClazz.getDeclaredMethods();
        //把method中的注解数据提取出来封装到methodHandler里面去
        for (Method method:methods){
           if (method.isAnnotationPresent(MyRequestLine.class)){
             MyRequestLine myRequestLine=method.getAnnotation(MyRequestLine.class);
             String urlStr= getUrlStr(myRequestLine);
               if (TextUtil.isBlank(urlStr)){
                   return null;
               }
             MyRequestMethod myRequestMethod=getRequestMethod(myRequestLine);
             MyRequestInfo myRequestInfo=new MyRequestInfo(urlStr,myRequestMethod,null);
             MethodHandler methodHandler= (MethodHandler) ReflectUtils.createObject(handlerClazz);

             methodHandler.setMethod(method);
             methodHandler.setMyRequestInfo(myRequestInfo);

             handlers.add(methodHandler);
           }
        }

        RequestJdkProxy requestJdkProxy=new RequestJdkProxy(requestClientClazz,handlers);
        Object requestClient=requestJdkProxy.getProxy();
        return (T) requestClient;
    }


    private  MyRequestMethod getRequestMethod(MyRequestLine myRequestLine) {
       return myRequestLine.method();
    }

    /**
     * 得到url  以value为主 如果value为空 那么用path里面的字段。
     * @param myRequestLine
     * @return
     */
    private  String getUrlStr(MyRequestLine myRequestLine) {
        String urlStr=null;
        String value=myRequestLine.value();
        String path=myRequestLine.path();
        if (TextUtil.isNotBlank(value)){
            urlStr=value;
        }
        if (TextUtil.isBlank(urlStr)&&TextUtil.isNotBlank(path)){
            urlStr=path;
        }
        return urlStr;
    }
}
