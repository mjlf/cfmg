package com.mjlf.cfmg.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class SessionFactoryUtil {
	private static SessionFactory sessionFactory;
	
	static{
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static Session getSession(){
		return sessionFactory.openSession();
	}
}
