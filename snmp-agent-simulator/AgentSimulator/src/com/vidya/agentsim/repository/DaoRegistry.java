package com.mindtree.agentsim.repository;


import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoRegistry {
    private static ApplicationContext ctx;

    static {
        ctx = new ClassPathXmlApplicationContext("context.xml");
    }

    /**
     * Private to make this a singleton.
     */
    private DaoRegistry(){

    }

    public static SessionFactory getSessionFactory(){
        return (SessionFactory) ctx.getBean("factory", SessionFactory.class);
    }

    public static RepositoryDao getEventDao(){
        return (RepositoryDao)ctx.getBean("eventDao", RepositoryDao.class);
    }
}
