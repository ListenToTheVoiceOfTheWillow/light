package com.mrchen.light.myrequest.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

    public static Class<?> getTypeByFieldName(Class<?> clazz, String name) {
        try {
            Field field=clazz.getDeclaredField(name);
            return field.getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
      return null;
    }

    public static Object createObject(Class<?> clazz,Object... args) {
        try {
            Constructor constructor=clazz.getConstructor();
            Object object=constructor.newInstance(args);
            return object;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setProperty(Object singleton, String name, Object valueToUse) {
        Class<?> clazz=singleton.getClass();
        try {
            Field field=clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(singleton,valueToUse);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void invokeMethod(Object singleton, String initMethod) {
        Class<?> clazz=singleton.getClass();
        try {
            Method method=clazz.getDeclaredMethod(initMethod);
            method.setAccessible(true);
            method.invoke(singleton);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
