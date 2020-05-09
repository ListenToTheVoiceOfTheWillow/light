package com.mrchen.light.myrequest.utils;

/**
 * @program: wechat-demo
 * @description: 字符串操作工具
 * @author: mrchen
 * @create: 2020-05-08 08:13
 */
public class TextUtil {
    /**
     * 是否字符串为空
     * @param str 字符串
     * @return  true 字符串为空  false字符串不为空
     */
    public static boolean isBlank(String str){
        if (str==null||"".equals(str)){
            return true;
        }
        return false;
    }

    /**
     * 是否字符串不为空
     * @param str 字符串
     * @return  true 字符串不为空， false字符串为空
     */
    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }
}
