package com.thanple.thinking.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * Created by Thanple on 2016/8/23 0023.
 */

public class JsoupSpiderUtil {

    static{
        initSSLContext();
    }

    /**
     * 获取有效的网站的Ducument：超时5000ms，连接失败时继续连接
     * @param String strURL 要访问的网站
     * @return Document
     * */
    public static Document getEnableConnection(String strURL){

        Document doc = null;
        while(null == doc) {
            try {
                doc = Jsoup.connect(strURL)
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
                        .timeout(5000).get();
            } catch (IOException e) {
                System.out.println("网络连接失败:" + e.getMessage());
            }
        }
        return doc;
    }

    public static Element getFirstElementByTag(Element e , String tag){
        return e.getElementsByTag(tag).first();
    }
    public static Element getFirstElementByTag(Document d , String tag){
        return d.getElementsByTag(tag).first();
    }
    public static Element getFirstElementByClassName(Element e,String cls){
        return e.getElementsByClass(cls).first();
    }




    /**
     * 初始化SSLContext：当访问https时需要调用此函数
     * */
    private static void initSSLContext() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                    //return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
