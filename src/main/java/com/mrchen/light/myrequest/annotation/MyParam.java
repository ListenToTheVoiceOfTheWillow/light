package com.mrchen.light.myrequest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: wechat-demo
 * @description: 这个字段可以替换url中的字段
 * @author: mrchen
 * @create: 2020-05-08 10:39
 */
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyParam {
   String value() default "";
}
