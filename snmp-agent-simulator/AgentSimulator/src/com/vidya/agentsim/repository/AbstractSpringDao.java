package com.mindtree.agentsim.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractSpringDao  extends HibernateDaoSupport{

	public AbstractSpringDao() { }

	protected void saveOrUpdate(Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	public void saveOrUpdate(List objList)
	{
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		int counter = 0;
		for (Object obj : objList)
		{ 
			session.save(obj);
			if ( counter % 100 == 0 )
			{
				session.flush();
				session.clear();
			}
			counter ++;
		}
		session.close(); 
	}

	protected void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}

	protected Object find(Class clazz, Long id) {
		return getHibernateTemplate().load(clazz, id);
	}

	protected List findAll(Class clazz) {
		return getHibernateTemplate().find("from " + clazz.getName());
	}
}
