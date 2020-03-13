package com.thanple.thinking.util;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @Author: Liuzhipeng05
 * @Date: 2020-02-28 13:09
 * @Description:
 */
public class SearchAlex {


    private HttpClientContext context;
    private CloseableHttpClient httpClient;


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


    public Document getDocument(String url){
        HttpGet newGet = new HttpGet(url);
        //设置请求报文头的编码
        newGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        //设置期望返回的报文头编码
        newGet.setHeader("Accept", "text/plain;charset=utf-8");
        try {
            CloseableHttpResponse res = httpClient.execute(newGet, context);
            String content = EntityUtils.toString(res.getEntity(), "UTF-8");

            Document doc = Jsoup.parse(content);

            return doc;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        String basicUrl = "https://www.859mk.com";
        String pageBasicUrl = basicUrl+"/down/2";
        String pageTemplate = pageBasicUrl+"/index_%s.html";

        SearchAlex alex = new SearchAlex();
        alex.createContext();
        for(int i = 1;i<=100;i++) {

            String page = String.format(pageTemplate,i+"");
            if(i == 1) {
                page = pageBasicUrl;
            }

            System.out.println(page);

            Document document = alex.getDocument(page);

            Element box_movie2_list = document.getElementsByClass("movie2_list").first();

            Elements hrefs = box_movie2_list.getElementsByTag("a");
            for(Element e : hrefs) {
                String detailUrl = basicUrl + e.attr("href");
                String title = e.getElementsByTag("h3").first().text();

                if(title.contains("吉")) {
                    System.out.println(detailUrl);
                    System.out.println(title);
                }

            }
        }

        alex.closeContext();
    }
}
