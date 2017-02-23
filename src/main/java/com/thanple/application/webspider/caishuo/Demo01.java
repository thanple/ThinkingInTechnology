package com.thanple.application.webspider.caishuo;

import com.thanple.application.webspider.caishuo.Dao.HibernateUtil;
import com.thanple.application.webspider.caishuo.Model.Basket;
import com.thanple.application.webspider.caishuo.Model.BasketInf;
import lombok.Data;
import org.hibernate.Session;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thanple on 2016/8/23 0023.
 */

//个股封装类
@Data
class BasketBean{
    private String href;    //链接是？

    private String title;   //标题
    private String author;  //作者
    private String time;    //时间
    private Integer attentions; //关注数
    private String tags;    //组合标签

    private List<Map<String,List<Map<String,String>>>> listInfs =
            new ArrayList<Map<String, List<Map<String, String>>>>(); //需要的信息列表


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(href).append("  ").append(title).append("\n");
        for(Map<String,List<Map<String,String>>> eachIndustry : listInfs){  //n个行业
            for(Map.Entry<String,List<Map<String,String>>> entry : eachIndustry.entrySet()){
                String industry = entry.getKey();   //行业名
                builder.append("************* ").append(industry).append(" *************\n");
                List<Map<String,String>> listInf = entry.getValue();    //行业的n条信息
                for(Map<String,String> eachInf : listInf){
                    builder.append(eachInf.get("rate")).append("   ");
                    builder.append(eachInf.get("code")).append("   ");
                    builder.append(eachInf.get("company")).append("   ");
                    builder.append(eachInf.get("reason")).append("\n");
                }
            }
        }

        return new String(builder);
    }
}

public class Demo01 {

    public final String urlBase = "https://www.caishuo.com";    //财说地址
    public final String urlTxh = urlBase + "/baskets";          //投组合地址

    public final int NUM_PAGES = 274;    //https://www.caishuo.com/baskets 投组合的页数

    public String getEachPageURL(int nPage){
        return nPage==1 ? urlTxh : urlTxh+"?page="+nPage;
    }

    /**
     * 获取所有投组合的所有模块地址 : 274页
     * */
    public void catchBaskets(){

        //将每一页的baskets内容存入list
        for(int i=1;i<=NUM_PAGES;i++){

            System.out.println("****************************第"+i+"页****************************");

            Document doc = JsoupSpiderUtil.getEnableConnection( getEachPageURL(i) );
            for(Element e : doc.getElementsByClass("contentlist").first().getElementsByTag("li") ){

                //获取href
                String href = urlBase + e.getElementsByTag("a").first().attr("href");

                //抓取标题
                String title = e.getElementsByClass("name").first().html();

                //抓取每个href的内容
                BasketBean basketBean = this.catchEachItem(href);
                basketBean.setTitle(title);
                basketBean.setHref(href);

                //存进数据库
                this.saveToDataBase(basketBean);
            }
        }

    }



    /**
     * 每个模块的内容进行抓取
     */
    public BasketBean catchEachItem(String href){

        BasketBean basketBean = new BasketBean();

        Document doc = JsoupSpiderUtil.getEnableConnection( href);

        //抓取作者
        Element eff = doc.getElementsByClass("doubleBox_right").first();
        Element dd = eff.getElementsByTag("dd").first();
        String author = null;
        try {
            author = StringUtil.filterOffUtf8Mb4( dd.getElementsByTag("a").first().html() );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //抓取发表日期
        String createTime = dd.childNode(4).toString().replace("发表：","").replace(" ","");

        //关注数
        Element container = doc.getElementsByClass("small_title").first();
        int attentions = Integer.parseInt( container.getElementsByTag("span").first()
                .getElementsByTag("em").first().html() );

        //组合标签
        Elements lis = container.getElementsByTag("li");
        StringBuilder builder = new StringBuilder();
        for(Element e : lis){
            builder.append(e.getElementsByTag("a").first().html()).append(";");
        }
        String tags = new String(builder);

        basketBean.setAttentions(attentions);
        basketBean.setAuthor(author);
        basketBean.setTags(tags);
        basketBean.setTime(createTime);


        for(Element e : doc.getElementById("stock-portfolio").getElementsByTag("tbody")){

            Elements trs = e.getElementsByTag("tr");
            Map<String,List<Map<String,String>>> mapTR = new HashMap<String, List<Map<String, String>>>();

            //所有行业
            String industry = null;
            List<Map<String,String>> listDustryInf = new ArrayList<Map<String, String>>();
            for(int i=0;i<trs.size();i++){
                Element tr = trs.get(i);

                if(0 == i){ //第一个tr为所属行业，其他为该行业的具体信息List
                    industry = tr.getElementsByClass("sector").first().text();
                }else{

                    Map<String,String> inf = new HashMap<String, String>();

                    //每个行业的所有数据
                    Elements tdsInf = tr.getElementsByTag("td");
                    inf.put("rate",tdsInf.get(0).html());
                    inf.put("code",tdsInf.get(1).getElementsByTag("a").first().html());
                    inf.put("company",tdsInf.get(2).getElementsByTag("a").first().html());
                    inf.put("reason",tdsInf.get(8).child(0).html());

                    listDustryInf.add(inf);

                }
            }

            mapTR.put(industry,listDustryInf);  //每个行业的map

            basketBean.getListInfs().add(mapTR); //每个模块有多个行业
        }

        return basketBean;
    }

    /**
     * 将消息进行入库；数据结构BasketBean
     * */
    public void saveToDataBase(BasketBean basketBean){
        System.out.println(basketBean);

        Session session = HibernateUtil.currentSession();
        session.beginTransaction();

        //保存链接和标题，作者等信息
        Basket basket = new Basket();
        basket.setTitle(basketBean.getTitle());
        basket.setHref(basketBean.getHref());
        basket.setAuthor(basketBean.getAuthor());
        basket.setTime(basketBean.getTime());
        basket.setAttentions(basketBean.getAttentions());
        basket.setTags(basketBean.getTags());

        session.save(basket);

        //保存每个行业的信息
        long basketId = basket.getId();

        for(Map<String,List<Map<String,String>>> eachIdustry : basketBean.getListInfs()){
            for(Map.Entry<String,List<Map<String,String>>> entry : eachIdustry.entrySet()) {
                String industry = entry.getKey();   //行业名
                List<Map<String,String>> listInf = entry.getValue();    //行业的n条信息
                for(Map<String,String> eachInf : listInf){

                    BasketInf inf = new BasketInf();
                    inf.setBasketId(basketId);
                    inf.setIndustry(industry);
                    inf.setCode(eachInf.get("code"));
                    inf.setRate(eachInf.get("rate"));
                    inf.setCompany(eachInf.get("company"));
                    inf.setCompany(eachInf.get("reason"));

                    session.save(inf);
                }
            }
        }

        session.getTransaction().commit();
    }

    public static void main(String[] args){

        long start = System.currentTimeMillis();

        Demo01 d = new Demo01();
        d.catchBaskets();

        long time = System.currentTimeMillis() - start;
        System.out.println("总共用时："+time+"ms");
    }

}
