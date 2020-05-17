package com.mrchen.light.test;

import com.mrchen.light.myrequest.factory.MyRequestLineFactory;
import net.sf.json.JSONObject;

/**
 * @program: light-framework-custom
 * @description: 运行测试类 MyRequestClient为网络申请的类 ， TestMethodHandler为 具体网络申请的实现类 可自己写
 *                最后运行结果为：
 *                JSON:   {"errcode":40013,"errmsg":"invalid corpid, hint: [1589708713_50_790b28916c092728bcc8d59b193b1b17], from ip: 111.227.254.169, more info at https://open.work.weixin.qq.com/devtool/query?e=40013"}
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
