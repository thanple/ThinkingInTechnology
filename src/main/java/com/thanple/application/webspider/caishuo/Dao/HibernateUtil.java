package com.thanple.application.webspider.caishuo.Dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaValidator;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static final ThreadLocal m_session = new ThreadLocal();

    static {
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }catch(HibernateException ex){
            throw new RuntimeException("创建SessionFactory失败: " + ex.getMessage(), ex);
        }
    }

    public static Session currentSession() throws HibernateException {
        Session s = (Session) m_session.get();
        if (s == null) {
            s = sessionFactory.openSession();
            m_session.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session) m_session.get();
        m_session.set(null);
        if (s != null)
            s.close();
    }
}
