package com.thanple.gameserver.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thanple on 2019/6/4.
 */
public class WorkTask {

    private static final String basicUrl = "http://gametask.sys.ledo.com";
    private static final String loginCheckUrl = basicUrl + "/j_spring_security_check";
    private static final String taskInfo = basicUrl + "/task/ajax";
    private static final String taskDetailInfo = basicUrl + "/task/show/";

    private HttpClientContext context;
    private CloseableHttpClient httpClient;


    public static void main(String[] args){
        WorkTask workTask = new WorkTask();
        workTask.createContext();
        workTask.login();
        List<String> zabbixServers = workTask.startTask();
        workTask.closeContext();


//        for(String zabbixServer : zabbixServers) {
//            ZabbixTask zabbixTask = new ZabbixTask(zabbixServer);
//            zabbixTask.createContext();
//            zabbixTask.login();
//            zabbixTask.findAndClick();
//            zabbixTask.closeContext();
//        }
    }

    public List<String> startTask(){
        String taskInfoStr = getTaskInfo();
        System.out.println("工单信息="+taskInfoStr);


        List<String> zabbixServers = new ArrayList<>();
        for(Object o : JSON.parseArray(taskInfoStr)){
            JSONObject jsonObject = (JSONObject)o;

            String openTime = jsonObject.getString("start");
            SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH");
            if(openTime.startsWith(aDate.format(System.currentTimeMillis()))){
                String taskId = jsonObject.get("id").toString();
                String detailStr = getTaskDetail(taskDetailInfo + taskId);

                System.out.println("处理工单"+jsonObject);

                String android = "<td class=\"td-oneline\">安卓-";
                String ios = "<td class=\"td-oneline\">IOS-";

                int index_android = detailStr.indexOf(android);
                if(index_android >= 0){
                    String and_server = String.format("kofgame%s-and",detailStr.substring(index_android + android.length() ,  detailStr.indexOf("服",index_android) ));
                    System.out.println("开始处理"+and_server);
                    zabbixServers.add(and_server);
                }
                int index_ios = detailStr.indexOf(ios);
                if(index_ios >= 0){
                    String ios_server = String.format("kofgame%s-ios",detailStr.substring(index_ios + ios.length()  ,  detailStr.indexOf("服",index_ios) ));
                    System.out.println("开始处理"+ios_server);
                    zabbixServers.add(ios_server);
                }

                if(jsonObject.getIntValue("status") == 2){
                    //workTask.start(taskId,2);
                    //workTask.start(taskId,3);
                }else {
                    System.out.println("工单状态为"+jsonObject.get("status"));
                }
            }
        }
        return zabbixServers;
    }



    private void createContext(){
        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        // 创建一个HttpClient
        httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();
    }

    private void closeContext(){
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void login(){
        // 构造post数据
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair("j_username", "liuzhipeng000929@ledo.com"));
        valuePairs.add(new BasicNameValuePair("j_password", "123456"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        entity.setContentType("application/x-www-form-urlencoded");

        // 创建一个post请求
        HttpPost post = new HttpPost(loginCheckUrl);
        // 注入post数据
        post.setEntity(entity);
        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(post);
            res.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("登陆成功后,新的Cookie:===============");
//        for (Cookie c : context.getCookieStore().getCookies()) {
//            System.out.println(c.getName() + ": " + c.getValue());
//        }
    }

    private String getTaskDetail(String url){
        HttpPost post = new HttpPost(url);
//        List<NameValuePair> valuePairs2 = new LinkedList<NameValuePair>();
//        long now = System.currentTimeMillis();
//        SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd");
//        valuePairs2.add(new BasicNameValuePair("beginTime", aDate.format(now)));
//        valuePairs2.add(new BasicNameValuePair("endTime", aDate.format(now)));
//
//        UrlEncodedFormEntity entity2 = new UrlEncodedFormEntity(valuePairs2, Consts.UTF_8);
//        entity2.setContentType("application/x-www-form-urlencoded");
//        newGet2.setEntity(entity2);

        CloseableHttpResponse res = null;
        String content2 = null;
        try {
            res = httpClient.execute(post, context);
            content2 = EntityUtils.toString(res.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content2;
    }

    private String getTaskInfo(){
        HttpPost newGet2 = new HttpPost(taskInfo);
        List<NameValuePair> valuePairs2 = new LinkedList<NameValuePair>();
        long now = System.currentTimeMillis();
        SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd");
        valuePairs2.add(new BasicNameValuePair("beginTime", aDate.format(now)));
        valuePairs2.add(new BasicNameValuePair("endTime", aDate.format(now)));

        UrlEncodedFormEntity entity2 = new UrlEncodedFormEntity(valuePairs2, Consts.UTF_8);
        entity2.setContentType("application/x-www-form-urlencoded");
        newGet2.setEntity(entity2);

        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(newGet2, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content2 = null;
        try {
            content2 = EntityUtils.toString(res.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content2;
    }

    private void start(String taskId,int status){
        // 构造post数据
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair("taskid", taskId));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        entity.setContentType("application/x-www-form-urlencoded");

        // 创建一个post请求
        String url = taskInfo + "/" + (status == 2 ? "start.json":"finished.json");
        HttpPost post = new HttpPost(url);
        // 注入post数据
        post.setEntity(entity);
        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(post);
            res.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
