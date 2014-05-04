package com.billmanager.app.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billmanager.app.dao.CustomerDAO;
import com.billmanager.app.domain.auth.Customer;
import com.billmanager.app.utils.HibernateUtils;

@Service
@Transactional
@Component
public class CustomerDAOImpl implements CustomerDAO{

	public Customer findCustomer(String username, String password) {
		
	Session hibSession = HibernateUtils.getSessionFactory().openSession();	
		hibSession.beginTransaction();
		Query query = hibSession.createQuery("from Customer where username = :username AND password = :password");
		query.setParameter("username",username);
		query.setParameter("password",password);
		List customerList =  query.list();
		System.out.println("customer list is="+customerList);
		hibSession.close();
		if(customerList.size()>0)
		return (Customer)customerList.get(0);
		else
			return null;
	}

}
