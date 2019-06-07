package com.thanple.gameserver.framework;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thanple on 2019/6/6.
 */
public class ZabbixTask {

    private static final String basicUrl = "http://zabbix.sys.ledo.com/zabbix";
    private static final String loginCheckUrl = basicUrl + "/index.php";
    private static final String searchServerUrl = basicUrl + "/hosts.php?filter_host=%s&filter_monitored_by=0&filter_dns=&filter_ip=&filter_port=&filter_set=1";

    private HttpClientContext context;
    private CloseableHttpClient httpClient;

    private String server;
    public ZabbixTask(String server){
        this.server = server;
    }

    public static void main(String[] args){
        ZabbixTask zabbixTask = new ZabbixTask("kofgame1640-and");
        zabbixTask.createContext();
        zabbixTask.login();
        zabbixTask.findAndClick();
        zabbixTask.closeContext();
    }

    public void createContext(){
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

    public void closeContext(){
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void findAndClick(){
        HttpGet newGet = new HttpGet(String.format(searchServerUrl,server));
        try {
            CloseableHttpResponse res = httpClient.execute(newGet, context);
            String content = EntityUtils.toString(res.getEntity());
            Document doc = Jsoup.parse(content);
            Element list_table = doc.getElementsByClass("list-table").first();
            Element tr = list_table.getElementsByTag("tbody").first().getElementsByTag("tr").first();
            Element td = tr.getElementsByTag("td").get(10);
            String wannaClickStr = td.toString();

            System.out.println(wannaClickStr);
            if(wannaClickStr.contains("停用的")){
                String redirectTmp = wannaClickStr.substring(wannaClickStr.indexOf("redirect('")+"redirect('".length() , wannaClickStr.indexOf("', 'post'")).replaceAll("&amp;","&");
                String redirectSplit[] = redirectTmp.split("&sid=");

                String wannaClickUrl = basicUrl+"/"+redirectSplit[0];
                String sid = redirectSplit[1];

                click(wannaClickUrl,sid);

                System.out.println(String.format("开启zabbix%s完成，url=%s,sid=%s",server, wannaClickUrl,sid));
            }else {
                System.out.println("zabbix已经开启，无需重复开启");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void login() {
        // 构造post数据
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair("name", "liuzhipeng"));

        valuePairs.add(new BasicNameValuePair("password", "liuzhipeng!@#$"));
        valuePairs.add(new BasicNameValuePair("enter", "登录"));

        valuePairs.add(new BasicNameValuePair("autologin", "1"));
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

    }

    private void click(String url,String sid){
        // 构造post数据
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair("sid", sid));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        entity.setContentType("application/x-www-form-urlencoded");


        // 创建一个post请求
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        // 注入post数据
        post.setEntity(entity);
        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(post,context);
            //System.out.println(EntityUtils.toString(res.getEntity()));
            res.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
