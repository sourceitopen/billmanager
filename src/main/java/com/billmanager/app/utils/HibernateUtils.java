package com.billmanager.app.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory()
	{
		if(sessionFactory==null)
		//sessionFactory=new Configuration().configure().addPackage("beans").buildSessionFactory();
			
		sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		return sessionFactory;
	}
}
