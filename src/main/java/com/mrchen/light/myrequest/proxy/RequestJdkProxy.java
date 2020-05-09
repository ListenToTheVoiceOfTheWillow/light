package com.mrchen.light.myrequest.proxy;


import com.mrchen.light.myrequest.annotation.MyJsonParam;
import com.mrchen.light.myrequest.annotation.MyParam;
import com.mrchen.light.myrequest.handler.support.MethodHandler;
import com.mrchen.light.myrequest.utils.GenericTokenParser;
import com.mrchen.light.myrequest.utils.TextUtil;
import com.mrchen.light.myrequest.utils.TokenHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: wechat-demo
 * @description:
 * @author: mrchen
 * @create: 2020-05-07 17:07
 */
public class RequestJdkProxy<T> implements InvocationHandler {
    private Class<T> proxyInterface;
    private List<MethodHandler> methodHandlers;
    //获取url替换参数的前缀
    private final String  paramGetSymbolPrefix="{";
    //获取url替换参数的后缀
    private final String  paramGetSymbolSuffix="}";
    public RequestJdkProxy(Class<T> proxyInterface,List<MethodHandler> methodHandlers) {
        this.proxyInterface = proxyInterface;
        this.methodHandlers=methodHandlers;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodHandler requestMethodHandler=null;
        for (MethodHandler methodHandler:methodHandlers){
            if (checkIsSameMethod(methodHandler.getMethod(),method)){
                requestMethodHandler=methodHandler;
                break;
            }
        }
        if (requestMethodHandler==null){
            return null;
        }
        resetParams(method,requestMethodHandler,args);
        Object result= doRequest(requestMethodHandler);
        return result;
    }

    /**
     * 设置传入的参数
     * @param method
     * @param requestMethodHandler
     */
    private void resetParams(Method method, MethodHandler requestMethodHandler,Object[] args) {
        //获取之前注解中的url 下面步骤可能对它进行一些字段替换
        String urlStr=requestMethodHandler.getMyRequestInfo().getUrl();
        Parameter[] parameters= method.getParameters();
        //将要转换成json的String
        Map<String,Object> convertJsonParams=new HashMap<>();
        //将要改变url字段的
        Map<String,String> urlChangeParams=new HashMap<>();
        //本身的JSON字段
        JSONObject jsonOriginal=null;
        //处理数据到  convertJsonParams, urlChangeParams ,jsonOriginal
        jsonOriginal=dealData(parameters,args,convertJsonParams,urlChangeParams,jsonOriginal);
        //生成请求的参数 如果有@MyJsonParam只取 @MyJsonParam的json，如果就把 没有注解的字段取出来，生成JSON
        JSONObject paramJson= generateParam(convertJsonParams,jsonOriginal);
        requestMethodHandler.getMyRequestInfo().setRequestParams(paramJson.toString());
        //对url进行修改
        String handledUrl= changeUrlStr(urlStr,urlChangeParams);
        //把处理之后的url传入到封装的请求的信息中去。
        requestMethodHandler.getMyRequestInfo().setUrl(handledUrl);
    }

    private JSONObject generateParam(Map<String, Object> convertJsonParams, JSONObject jsonOriginal) {
        if (jsonOriginal!=null){
           return jsonOriginal;
        }
        jsonOriginal=new JSONObject();
         for (Map.Entry<String,Object> entry:convertJsonParams.entrySet()){
             if (entry.getValue() instanceof String ){
                 if (TextUtil.isNotBlank((String) entry.getValue())){
                     jsonOriginal.put(entry.getKey(), entry.getValue());
                 }

             }else if (entry.getValue() instanceof JSONArray){
                 jsonOriginal.put(entry.getKey(),entry.getValue());
             }
         }
         return jsonOriginal;
    }

    private String changeUrlStr(String urlStr,Map<String, String> urlChangeParams) {
        GenericTokenParser tokenParser = new GenericTokenParser("{", "}", new BindingTokenParser(urlChangeParams));
        String handledUrl=tokenParser.parse(urlStr);
        return handledUrl;
    }

    private JSONObject dealData(Parameter[] parameters, Object[] args, Map<String, Object> convertJsonParams, Map<String, String> urlChangeParams, JSONObject jsonOriginal) {
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter=parameters[i];
            Class<?> paramClazz=parameter.getType();
            Object arg=args[i];
            if (parameter==null || arg==null){
                continue;
            }
            MyParam myParam= parameter.getDeclaredAnnotation(MyParam.class);
            MyJsonParam myJsonParam=parameter.getDeclaredAnnotation(MyJsonParam.class);
            if (arg.getClass()==paramClazz || paramClazz.isAssignableFrom(arg.getClass())){
                if (myParam==null){
                    if (myJsonParam==null){
                        //转换成json字符串的传入的字段
                        putJsonParam(convertJsonParams,parameter.getName(),arg);
                    }else {
                        //JSONObject的字段
                        if (arg instanceof JSONObject) {
                            jsonOriginal = (JSONObject) arg;
                        }
                    }
                }else {
                    if (TextUtil.isNotBlank(myParam.value())){
                        putUrlChangeParam(urlChangeParams,myParam.value(),arg);
                    }
                }
            }
        }
        return jsonOriginal;
    }

    private void putUrlChangeParam(Map<String, String> urlChangeParams, String name, Object arg) {
        String convertedParam=null;
        if (arg instanceof String){
            convertedParam= (String) arg;
        }else if (arg instanceof Integer){
            convertedParam=String.valueOf(arg);
        }else if (arg instanceof Boolean){
            convertedParam=String.valueOf(arg);
        }
        urlChangeParams.put(name,convertedParam);
    }

    private void putJsonParam(Map<String, Object> jsonParams, String name,Object arg) {
        String convertedParam=null;
        JSONArray convertedParamJsonArray=null;
        if (arg instanceof String){
          convertedParam= (String) arg;
        }else if (arg instanceof Integer){
         convertedParam=String.valueOf(arg);
        }else if (arg instanceof Boolean){
            convertedParam=String.valueOf(arg);
        } else if (arg instanceof Map){
            //TODO
        } else if (arg instanceof List){
            convertedParamJsonArray=new JSONArray();
            convertedParamJsonArray.addAll((List)arg);
        }
        if (convertedParam!=null){
            jsonParams.put(name,convertedParam);
        }
        if (convertedParamJsonArray!=null){
            jsonParams.put(name,convertedParamJsonArray);
        }


    }

    /**
     * 检查是否是一样的method
     * @param method
     * @param method1
     * @return
     */
    private boolean checkIsSameMethod(Method method, Method method1) {
       if (method.getName()!=method1.getName()){
           return false;
       }
       if (method.getParameterCount()!=method1.getParameterCount()){
           return false;
       }

       //TODO 还有一些校验
        return true;
    }

    /**
     * 真正发送请求
     * @param requestMethodHandler
     */
    private Object doRequest(MethodHandler requestMethodHandler) {
       return   requestMethodHandler.doRequest();
    }

    public T getProxy(){
        return (T) Proxy.newProxyInstance(proxyInterface.getClassLoader(),new Class[]{proxyInterface},this);
    }

    private class BindingTokenParser implements TokenHandler {
        private Map<String,String> urlChangeParams;
        public BindingTokenParser(Map<String,String> urlChangeParams){
            this.urlChangeParams=urlChangeParams;
        }
        @Override
        public String handleToken(String content) {
            return urlChangeParams.get(content);

        }
    }
}
