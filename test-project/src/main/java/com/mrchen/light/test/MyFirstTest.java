package com.mrchen.light.test;

import com.mrchen.light.myrequest.factory.MyRequestLineFactory;
import net.sf.json.JSONObject;

/**
 * @program: light-framework-custom
 * @description:
 * @author: mrchen
 * @create: 2020-05-09 15:12
 */
public class MyFirstTest {
    public static void main(String[] args){
         MyRequestClient myRequestClient= MyRequestLineFactory.getInstance().create(MyRequestClient.class, TestMethodHandler.class);
         JSONObject jsonObject= myRequestClient.getAccess_token("111","222");
         System.out.println("JSON:   "+jsonObject);
    }
}
