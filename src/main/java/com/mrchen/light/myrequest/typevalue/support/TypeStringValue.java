package com.mrchen.light.myrequest.typevalue.support;


import com.mrchen.light.myrequest.typevalue.TypeValue;

/**
 * @program: wechat-demo
 * @description: String字符串转换
 * @author: mrchen
 * @create: 2020-05-09 10:40
 */
public class TypeStringValue implements TypeValue {


    @Override
    public String parse(Object rawValue) {
        String valuetoUse=null;
        if (rawValue instanceof Integer){
            valuetoUse=String.valueOf(rawValue);
        }else if (rawValue instanceof String){
            valuetoUse= (String) rawValue;
        }else if (rawValue instanceof Boolean){
            valuetoUse=String.valueOf(rawValue);
        }else{
            return null;
        }

        return valuetoUse;
    }
}
