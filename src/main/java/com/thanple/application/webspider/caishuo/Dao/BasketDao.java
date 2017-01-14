package com.thanple.application.webspider.caishuo.Dao;


import com.thanple.application.webspider.caishuo.Model.Basket;
import org.hibernate.classic.Session;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class BasketDao {

    public long insert(String title,String href){
        Session session = HibernateUtil.currentSession();

        session.beginTransaction();

        Basket basket = new Basket();
        basket.setTitle(title);
        basket.setHref(href);

        session.save(basket);
        session.getTransaction().commit();

        return basket.getId();
    }
}
