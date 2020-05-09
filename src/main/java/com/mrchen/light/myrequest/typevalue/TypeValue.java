package com.mrchen.light.myrequest.typevalue;

/**
 * @program: wechat-demo
 * @description:
 * @author: mrchen
 * @create: 2020-05-09 10:38
 */
public interface TypeValue {

     <T> T parse(Object rawValue);
}
