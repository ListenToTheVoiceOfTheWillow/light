package com.mrchen.light.myrequest.annotation;
import com.mrchen.light.myrequest.requestinfo.MyRequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: wechat-demo
 * @description:
 * @author: mrchen
 * @create: 2020-05-07 16:25
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequestLine {
    String value() default "";
    String path()  default "";
    MyRequestMethod method() default MyRequestMethod.GET;


}
